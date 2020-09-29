package com.doraemon.wish.pack.controller;

import com.doraemon.wish.pack.dao.model.Plugin;
import com.doraemon.wish.pack.dao.model.PluginVersion;
import com.doraemon.wish.pack.service.PluginService;
import com.droaemon.common.util.FileUtil;
import com.droaemon.common.util.ZipUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/pack/plugins")
public class PluginController {

    private final PluginService pluginService;

    @Value("${pack.plugin-path}")
    private String pluginPath;

    public PluginController(PluginService pluginService) {
        this.pluginService = pluginService;
    }

    @PostMapping("")
    public Plugin createPlugin(@RequestBody Plugin plugin) {
        return pluginService.create(plugin);
    }

    @GetMapping("/{id}")
    public Plugin queryPlugin(@PathVariable Long id) {
        return pluginService.query(id);
    }

    @GetMapping("")
    public Page<Plugin> queryPluginByPage(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return pluginService.queryByPage(pageNo, pageSize);
    }

    @PatchMapping("/{id}/name")
    public Plugin updatePluginName(@PathVariable Long id, @RequestBody String name) {
        Plugin plugin = pluginService.query(id);
        plugin.setName(name);
        return pluginService.update(id, plugin);
    }

    @PostMapping("/{id}/version")
    public Plugin createPluginVersion(@PathVariable Long id, @RequestBody PluginVersion version) {
        Plugin plugin = pluginService.query(id);
        plugin.getVersions().add(version);
        return pluginService.update(id, plugin);
    }

    @DeleteMapping("/{id}")
    public void deletePlugin(@PathVariable Long id) {
        pluginService.delete(id);
    }

    @PostMapping("/version/zip")
    public String uploadPluginVersion(@RequestParam("zip") MultipartFile versionFile) throws IOException {
        // 保存ZIP文件
        FileUtil.saveMultiFile(pluginPath, versionFile);

        // 解压ZIP文件
        String fileName = versionFile.getResource().getFilename();
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        File zipFile = new File(pluginPath, fileName);
        ZipUtil.unZipFiles(zipFile.getAbsolutePath(), pluginPath);

        // 删除ZIP文件
        zipFile.delete();

        return name;
    }
}
