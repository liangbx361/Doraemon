package com.doraemon.wish.pack.controller;

import com.doraemon.wish.pack.service.ApkService;
import com.droaemon.common.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/pack/apk")
public class ApkController {

    private final ApkService apkService;

    @Value("${pack.apk-path}")
    private String apkPath;

    public ApkController(ApkService apkService) {
        this.apkService = apkService;
    }

    @PostMapping("/upload")
    public String uploadApk(@RequestParam("apk") MultipartFile apkFile) {
        // 保存apk文件
        FileUtil.saveMultiFile(apkPath, apkFile);
        // 获取文件名
        return apkFile.getResource().getFilename();
    }
}
