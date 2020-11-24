package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.*;
import com.doraemon.wish.pack.dao.repository.*;
import com.doraemon.wish.pack.model.PackConfig;
import com.doraemon.wish.pack.model.PackPlugin;
import com.doraemon.wish.pack.util.PluginUtil;
import com.droaemon.common.util.FileUtil;
import com.droaemon.common.util.JsonMapperUtil;
import com.droaemon.common.util.ShellUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class BuildApkTaskService {

    private BuildTaskRepository buildTaskRepository;
    private GameRepository gameRepository;
    private ChannelRepository channelRepository;
    private PluginRepository pluginRepository;
    private BuildApkRepository buildApkRepository;

    @Value("${pack.apk-path}")
    private String apkPath;
    @Value("${pack.build-path}")
    private String buildPath;
    @Value("${pack.plugin-path}")
    private String pluginDir;
    @Value("${pack.build-apk-path}")
    private String buildApkPath;

    private Thread thread;

    public BuildApkTaskService(BuildTaskRepository buildTaskRepository, GameRepository gameRepository,
                               ChannelRepository channelRepository, PluginRepository pluginRepository,
                               BuildApkRepository buildApkRepository) {
        this.buildTaskRepository = buildTaskRepository;
        this.gameRepository = gameRepository;
        this.channelRepository = channelRepository;
        this.pluginRepository = pluginRepository;
        this.buildApkRepository = buildApkRepository;

        startBuildLoop();
    }

    public BuildTask create(BuildTask task) {
        task.setStatus(BuildTask.Status.CREATE);
        task.setCreateTime(new Date());
        return buildTaskRepository.save(task);
    }

    public Page<BuildTask> queryByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        return buildTaskRepository.findAll(pageable);
    }

    private void startBuildLoop() {
        thread = new Thread(() -> {
            while (true) {
                Optional<BuildTask> taskOption = buildTaskRepository.findFirstByStatusEquals(BuildTask.Status.CREATE);
                if (taskOption.isPresent()) {
                    runBuild(taskOption.get());
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * 1. 生成构建配置文件
     * 2. 构建母包
     * 3. 上传母包到指定位置
     */
    private void runBuild(BuildTask task) {
        for (Long channelId : task.getChannelIds()) {
            Game game = gameRepository.findById(task.getGameId()).get();
            Channel channel = channelRepository.findById(channelId).get();

            PackConfig packConfig = new PackConfig();
            packConfig.game = game.getCode();
            packConfig.channel = channel.getCode();
            packConfig.type = channel.getType();
            packConfig.packageName = channel.getPackageName();

            PackPlugin packPlugin = new PackPlugin();
            try {
                packPlugin = JsonMapperUtil.stringToObject(channel.getPluginConfig(), PackPlugin.class);
                for(PackPlugin.Item item : packPlugin.items) {
                    Plugin plugin = pluginRepository.findById(item.pluginId).get();
                    PluginVersion pluginVersion;
                    if(item.versionId == -1) {
                        pluginVersion = PluginUtil.getLatestVersion(plugin);
                    } else {
                        pluginVersion = PluginUtil.getVersion(plugin, item.versionId);
                    }
                    item.name = pluginVersion.getFileName();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // plugin.json 配置转化失败
            }

            // 添加渠道参数插件
            PackPlugin.Item channelItem = new PackPlugin.Item();
            channelItem.name = "channel";
            packPlugin.items.add(channelItem);

            // 生成配置文件
            createJsonFile(packConfig, "config.json");
            createJsonFile(packPlugin, "plugin.json");
            createChannelFile(channel, buildPath);

            // 构建母包
            String filePath = apkPath + File.separator + task.getApkName();
            try {
                boolean success = ShellUtil.exec("g17173-pack -m -apk " + filePath + " -c " + buildPath + " -pluginDir " + pluginDir);
                if (success) {
                    task.setStatus(BuildTask.Status.SUCCESS);
                    buildTaskRepository.save(task);
                } else {
                    task.setStatus(BuildTask.Status.FAIL);
                    buildTaskRepository.save(task);
                    return;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                task.setStatus(BuildTask.Status.FAIL);
                buildTaskRepository.save(task);
                return;
            }

            // 查找母包
            File apkFile = findBuildApk();
            System.out.println("构建母包：" + apkFile.getAbsolutePath());

            File deskApkDir = new File(buildApkPath, game.getId().toString());
            if (!deskApkDir.exists()) {
                deskApkDir.mkdirs();
            }
            File destApkFile = new File(deskApkDir, apkFile.getName());
            try {
                FileCopyUtils.copy(apkFile, destApkFile);
                task.getBuildApks().add(destApkFile.getName());
                buildTaskRepository.save(task);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 保存构建的APk
            BuildApk buildApk = new BuildApk();
            buildApk.setGameId(game.getId());
            buildApk.setChannelId(channel.getId());
            buildApk.setCreateTime(new Date());
            buildApk.setApk(destApkFile.getName());
            buildApkRepository.save(buildApk);
        }
    }

    private void createJsonFile(Object modle, String fileName) {
        try {
            String modelJson = JsonMapperUtil.objectToString(modle);
            FileUtil.save(buildPath, fileName, modelJson.getBytes());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void createChannelFile(Channel channel, String buildPath) {
        switch (channel.getCode()) {
            case "c17173":
                createC17173File(channel, buildPath);
                break;

            case "o17173":
                createO17173File(channel, buildPath);
                break;
        }
    }

    private void createC17173File(Channel channel, String buildPath) {
        File channelDir = new File(buildPath, "channel");
        channelDir.mkdir();
        File assetsDir = new File(channelDir, "assets");
        assetsDir.mkdir();
        FileUtil.save(assetsDir.getAbsolutePath(), "g17173_init_config.json", channel.getConfig().getBytes());
    }

    private void createO17173File(Channel channel, String buildPath) {
        File channelDir = new File(buildPath, "channel");
        channelDir.mkdirs();
        File assetsDir = new File(channelDir, "assets");
        assetsDir.mkdir();
        FileUtil.save(assetsDir.getAbsolutePath(), "o17173_init_config.json", channel.getConfig().getBytes());
    }

    private File findBuildApk() {
        File buildDir = new File(buildPath);
        File[] apkfiles = buildDir.listFiles(pathname -> pathname.getAbsolutePath().endsWith(".apk"));
        return apkfiles[0];
    }

    private String createApkFileName(File file) {
        String name = file.getName();
        name = name.substring(0, name.length() - 4);

        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = timeFormat.format(new Date());

        return name + "_" + time + ".apk";
    }
}
