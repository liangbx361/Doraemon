package com.doraemon.wish.pack.controller;

import com.doraemon.wish.pack.dao.model.BuildTask;
import com.doraemon.wish.pack.service.BuildApkTaskService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public BuildTask createTask(@RequestBody BuildTask task) {
        return buildApkTaskService.create(task);
    }

    @GetMapping("")
    public Page<BuildTask> queryTaskByPage(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<BuildTask> taskPage =  buildApkTaskService.queryByPage(pageNo, pageSize);
        for(BuildTask item : taskPage.getContent()) {
            List<String> apkUrls = new ArrayList<>(item.getBuildApks().size());
            for(String apk : item.getBuildApks()) {
                String apkUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/doraemon/" + apk;
                apkUrls.add(apkUrl);
            }
            item.setBuildApks(apkUrls);
        }

        return taskPage;
    }
}
