package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.BuildApk;
import com.doraemon.wish.pack.dao.model.BuildChildApk;
import com.doraemon.wish.pack.dao.model.BuildChildApkTask;
import com.doraemon.wish.pack.dao.model.BuildTask;
import com.doraemon.wish.pack.dao.repository.BuildApkRepository;
import com.doraemon.wish.pack.dao.repository.BuildChildApkRepository;
import com.doraemon.wish.pack.dao.repository.BuildChildApkTaskRepository;
import com.droaemon.common.util.ShellUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
public class BuildChildApkTaskService {

    private BuildChildApkTaskRepository taskRepository;
    private BuildApkRepository apkRepository;
    private BuildChildApkRepository childApkRepository;

    @Value("${pack.build-apk-path}")
    private String buildApkPath;

    public BuildChildApkTaskService(BuildChildApkTaskRepository taskRepository, BuildApkRepository apkRepository,
                                    BuildChildApkRepository childApkRepository) {
        this.taskRepository = taskRepository;
        this.apkRepository = apkRepository;
        this.childApkRepository = childApkRepository;

        startBuildLoop();
    }

    public BuildChildApkTask create(BuildChildApkTask task) {
        task.setStatus(BuildTask.Status.CREATE);
        task.setCreateTime(new Date());
        return taskRepository.save(task);
    }

    public Page<BuildChildApkTask> queryByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        return taskRepository.findAll(pageable);
    }

    private void startBuildLoop() {
        new Thread(() -> {
           while (true) {
               System.out.println("run build child apk task loop");
               Optional<BuildChildApkTask> taskOptional = taskRepository.findFirstByStatusEquals(BuildTask.Status.CREATE);
               if(taskOptional.isPresent()) {
                   runBuild(taskOptional.get());
               } else {
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           }
        }).start();
    }

    private void runBuild(BuildChildApkTask task) {
        Optional<BuildApk> buildApkOptional = apkRepository.findById(task.getApkId());
        if(!buildApkOptional.isPresent()) {
            saveStatus(task, BuildTask.Status.FAIL);
            return;
        }
        BuildApk buildApk = buildApkOptional.get();
        File apkFile = new File(buildApkPath + File.separator + buildApk.getGameId().toString(), buildApk.getApk());

        // 构建子包
        try {
            boolean success = ShellUtil.exec("g17173-pack -sign " + task.getSign() + " -id " + task.getChildId() + " -apk " + apkFile.getAbsolutePath());
            if(!success) {
                task.setStatus(BuildTask.Status.FAIL);
                return;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            taskRepository.save(task);
            return;
        }

        // 拷贝到指定目录
        File childApkFile = new File(getFileName(apkFile.getName()) + "_" + task.getChildId() + ".apk");
        if(!childApkFile.exists()) {
            saveStatus(task, BuildTask.Status.FAIL);
            return;
        }
        File destApkFile = new File(buildApkPath + File.separator + buildApk.getGameId().toString(), childApkFile.getName());
        try {
            FileCopyUtils.copy(childApkFile, destApkFile);
        } catch (IOException e) {
            e.printStackTrace();
            saveStatus(task, BuildTask.Status.FAIL);
        }
        destApkFile.delete();

        BuildChildApk childApk = new BuildChildApk();
        childApk.setGameId(buildApk.getGameId());
        childApk.setApkId(buildApk.getId());
        childApk.setApk(destApkFile.getName());
        childApk.setCreateTime(new Date());
        childApkRepository.save(childApk);

        saveStatus(task, BuildTask.Status.SUCCESS);
    }

    private void saveStatus(BuildChildApkTask task, String status) {
        task.setStatus(status);
        taskRepository.save(task);
    }

    private String getFileName(String fileName) {
        return fileName.substring(0, fileName.length()-4);
    }
}