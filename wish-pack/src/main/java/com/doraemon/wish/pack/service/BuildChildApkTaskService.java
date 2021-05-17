package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.BuildApk;
import com.doraemon.wish.pack.dao.model.BuildChildApk;
import com.doraemon.wish.pack.dao.model.BuildChildApkTask;
import com.doraemon.wish.pack.dao.model.BuildApkTask;
import com.doraemon.wish.pack.dao.repository.BuildApkRepository;
import com.doraemon.wish.pack.dao.repository.BuildChildApkRepository;
import com.doraemon.wish.pack.dao.repository.BuildChildApkTaskRepository;
import com.doraemon.wish.pack.util.TimeUtil;
import com.droaemon.common.util.ShellUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class BuildChildApkTaskService {

    private BuildChildApkTaskRepository taskRepository;
    private BuildApkRepository apkRepository;
    private BuildChildApkRepository childApkRepository;

    @Value("${pack.build-apk-path}")
    private String buildApkBasePath;

    private LinkedBlockingQueue<BuildChildApkTask> mTaskQueue;

    public BuildChildApkTaskService(BuildChildApkTaskRepository taskRepository, BuildApkRepository apkRepository,
                                    BuildChildApkRepository childApkRepository) {
        this.taskRepository = taskRepository;
        this.apkRepository = apkRepository;
        this.childApkRepository = childApkRepository;

        mTaskQueue = new LinkedBlockingQueue<>();

        loadHistoryTask();
        runLoopThread();
    }

    private void loadHistoryTask() {
        List<BuildChildApkTask> buildingTasks = taskRepository.findAllByStatusEquals(BuildApkTask.BuildStatus.BUILDING);
        mTaskQueue.addAll(buildingTasks);
        List<BuildChildApkTask> createTasks = taskRepository.findAllByStatusEquals(BuildApkTask.BuildStatus.CREATE);
        mTaskQueue.addAll(createTasks);
    }

    public BuildChildApkTask create(BuildChildApkTask task) {
        task.setStatus(BuildApkTask.BuildStatus.CREATE);
        task.setCreateTime(new Date());
        task = taskRepository.save(task);

        try {
            mTaskQueue.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return task;
    }

    public Page<BuildChildApkTask> queryByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        return taskRepository.findAll(pageable);
    }

    private void runLoopThread() {
        new Thread(() -> {
           while (true) {
               System.out.println("Build child apk task -> loop");

               try {
                   BuildChildApkTask buildChildApkTask = mTaskQueue.take();
                   runBuild(buildChildApkTask);
                   System.out.println("Build child apk task -> runBuildTask");
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }).start();
    }

    private void runBuild(BuildChildApkTask task) {
        Optional<BuildApk> buildApkOptional = apkRepository.findById(task.getApkId());
        if(!buildApkOptional.isPresent()) {
            saveStatus(task, BuildApkTask.BuildStatus.FAIL);
            return;
        }

        BuildApk buildApk = buildApkOptional.get();
        String gameApkPath = buildApkBasePath + File.separator + buildApk.getPath();
        File motherApkFile = new File(gameApkPath, buildApk.getApk());

        // 构建子包
        try {
            ShellUtil.exec("g17173-pack -sign " + task.getSign()
                + " -id " + task.getChildId()
                + " -c " + gameApkPath
                + " -apk " + motherApkFile.getAbsolutePath());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            saveStatus(task, BuildApkTask.BuildStatus.FAIL);
            return;
        }

        // 判断是否生成成功
        String motherFileName = getFileName(motherApkFile.getName(), ".apk");
        String childApkName = motherFileName + "_" + task.getChildId() + ".apk";
        File childApkFile = new File(gameApkPath, childApkName);
        if(!childApkFile.exists()) {
            saveStatus(task, BuildApkTask.BuildStatus.FAIL);
            return;
        }

        BuildChildApk childApk = new BuildChildApk();
        childApk.setGameId(buildApk.getGameId());
        childApk.setApkId(buildApk.getId());
        childApk.setApk(childApkFile.getName());
        childApk.setCreateTime(new Date());
        childApkRepository.save(childApk);

        task.setApkPath(buildApk.getPath());
        task.setApk(childApkFile.getName());
        saveStatus(task, BuildApkTask.BuildStatus.SUCCESS);
    }

    private void saveStatus(BuildChildApkTask task, String status) {
        task.setStatus(status);
        taskRepository.save(task);
    }

    private String getFileName(String fileName, String suffix) {
        int index = fileName.indexOf(suffix);
        return fileName.substring(0, index);
    }
}
