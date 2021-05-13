package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.BuildApk;
import com.doraemon.wish.pack.dao.model.BuildChildApk;
import com.doraemon.wish.pack.dao.model.BuildChildApkTask;
import com.doraemon.wish.pack.dao.model.BuildApkTask;
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
        task.setStatus(BuildApkTask.Status.CREATE);
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
               Optional<BuildChildApkTask> taskOptional = taskRepository.findFirstByStatusEquals(BuildApkTask.Status.CREATE);
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
            saveStatus(task, BuildApkTask.Status.FAIL);
            return;
        }

        BuildApk buildApk = buildApkOptional.get();
        String gameApkPath = buildApkPath + File.separator + buildApk.getGameId().toString();
        File motherApkFile = new File(gameApkPath, buildApk.getApk());

        // 构建子包
        try {
            ShellUtil.exec("g17173-pack -sign " + task.getSign()
                + " -id " + task.getChildId()
                + " -c " + gameApkPath
                + " -apk " + motherApkFile.getAbsolutePath());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            saveStatus(task, BuildApkTask.Status.FAIL);
            return;
        }

        // 判断是否生成成功
        String motherFileName = getFileName(motherApkFile.getName(), ".apk");
        String childApkName = motherFileName + "_" + task.getChildId() + ".apk";
        File childApkFile = new File(gameApkPath, childApkName);
        if(!childApkFile.exists()) {
            saveStatus(task, BuildApkTask.Status.FAIL);
            return;
        }

        BuildChildApk childApk = new BuildChildApk();
        childApk.setGameId(buildApk.getGameId());
        childApk.setApkId(buildApk.getId());
        childApk.setApk(childApkFile.getName());
        childApk.setCreateTime(new Date());
        childApkRepository.save(childApk);

        task.setApk(childApkFile.getName());
        saveStatus(task, BuildApkTask.Status.SUCCESS);
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
