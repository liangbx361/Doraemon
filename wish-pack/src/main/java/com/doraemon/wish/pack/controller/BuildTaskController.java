package com.doraemon.wish.pack.controller;

import com.doraemon.wish.pack.dao.model.BuildApkTask;
import com.doraemon.wish.pack.service.BuildApkTaskService;
import com.doraemon.wish.pack.util.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pack/tasks")
public class BuildTaskController {

    private BuildApkTaskService buildApkTaskService;
    private HttpServletRequest request;

    public BuildTaskController(BuildApkTaskService buildApkTaskService, HttpServletRequest request) {
        this.buildApkTaskService = buildApkTaskService;
        this.request = request;
    }

    @PostMapping("")
    public BuildApkTask createTask(@RequestBody BuildApkTask task) {
        return buildApkTaskService.create(task);
    }

    @GetMapping("")
    public Page<BuildApkTask> queryTaskByPage(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<BuildApkTask> taskPage =  buildApkTaskService.queryByPage(pageNo, pageSize);
        for(BuildApkTask item : taskPage.getContent()) {
            List<String> apkUrls = new ArrayList<>(item.getBuildApks().size());
            for(String apk : item.getBuildApks()) {
                String apkUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/doraemon/"
                    + item.getGameId() + File.separator + TimeUtil.getFormatTime(item.getCreateTime()) + File.separator + apk;
                apkUrls.add(apkUrl);
            }
            item.setBuildApks(apkUrls);
        }

        return taskPage;
    }
}
