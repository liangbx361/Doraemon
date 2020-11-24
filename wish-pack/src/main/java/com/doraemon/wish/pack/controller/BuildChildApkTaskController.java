package com.doraemon.wish.pack.controller;

import com.doraemon.wish.pack.dao.model.BuildChildApkTask;
import com.doraemon.wish.pack.service.BuildChildApkTaskService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/pack/child-apk/tasks")
public class BuildChildApkTaskController {

    private BuildChildApkTaskService taskService;
    private HttpServletRequest request;

    public BuildChildApkTaskController(BuildChildApkTaskService taskService, HttpServletRequest request) {
        this.taskService = taskService;
        this.request = request;
    }

    @PostMapping("")
    public BuildChildApkTask createChildApkTask(@RequestBody BuildChildApkTask task) {
        return taskService.create(task);
    }

    @GetMapping("")
    public Page<BuildChildApkTask> queryChildApkTaskByPage(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return taskService.queryByPage(pageNo, pageSize);
    }
}
