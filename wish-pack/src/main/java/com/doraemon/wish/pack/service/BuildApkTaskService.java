package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.*;
import com.doraemon.wish.pack.dao.repository.*;
import com.doraemon.wish.pack.model.GameHubConfig;
import com.doraemon.wish.pack.model.PackConfig;
import com.doraemon.wish.pack.model.PackPlugin;
import com.doraemon.wish.pack.util.G17173PackUtil;
import com.doraemon.wish.pack.util.GameHubUtil;
import com.doraemon.wish.pack.util.JsonUtil;
import com.doraemon.wish.pack.util.PluginUtil;
import com.droaemon.common.util.FileUtil;
import com.droaemon.common.util.JsonMapperUtil;
import com.droaemon.common.util.ShellUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
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
                    runBuildTask(taskOption.get());
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
    private void runBuildTask(BuildTask task) {
        for (Long channelId : task.getChannelIds()) {
            cleanBuildDir(buildPath);

            Game game = gameRepository.findById(task.getGameId()).get();
            Channel channel = channelRepository.findById(channelId).get();

            GameHubConfig gameHubConfig = GameHubUtil.toConfig(game);
            gameHubConfig.gopId = channel.getCode();
            gameHubConfig.gopClassName = channel.getClassName();

            PackConfig packConfig = new PackConfig();
            packConfig.game = game.getCode();
            packConfig.channel = channel.getCode();
            packConfig.type = channel.getType();
            packConfig.packageName = channel.getPackageName();

            PackPlugin packPlugin = new PackPlugin();
            // 加入渠道插件配置 参数
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

            // 加入GameHub配置
            PackPlugin.Item gameHubItem = new PackPlugin.Item();
            gameHubItem.name = "gamehub-config";
            packPlugin.items.add(gameHubItem);

            // 加入GameHub插件和ETP配置

            // 加入渠道配置
            PackPlugin.Item channelItem = new PackPlugin.Item();
            channelItem.name = "channel-config";
            packPlugin.items.add(channelItem);

            // 生成配置文件
            try {
                createGamehubConfigFile(gameHubConfig, buildPath);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                // gamehub_init_config.json生成失败
            }
            try {
                createJsonFile(packConfig, "config.json");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                // config.json生成失败
            }
            try {
                createJsonFile(packPlugin, "plugin.json");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                // plugin.json生成失败
            }
            createChannelFile(channel, buildPath);

            // 构建母包
            String filePath = apkPath + File.separator + task.getApkName();
            try {
                ShellUtil.exec("g17173-pack -m -apk " + filePath + " -c " + buildPath + " -pluginDir " + pluginDir);
                if (G17173PackUtil.isPackSuccess()) {
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

    private void cleanBuildDir(String buildPath) {
        try {
            File buildFile = new File(buildPath);
            FileUtils.deleteDirectory(buildFile);
            buildFile.mkdirs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createJsonFile(Object model, String fileName) throws JsonProcessingException {
        String modelJson = JsonMapperUtil.objectToString(model);
        FileUtil.save(buildPath, fileName, modelJson.getBytes());
    }

    private void createGamehubConfigFile(GameHubConfig config, String buildPath) throws JsonProcessingException {
        File configDir = new File(buildPath, "gamehub-config");
        configDir.mkdir();
        File assetsDir = new File(configDir, "assets");
        assetsDir.mkdir();

        JsonUtil.modelToJsonFile(config, assetsDir.getAbsolutePath(), "gamehub_init_config.json");
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
        File channelDir = new File(buildPath, "channel-config");
        channelDir.mkdir();
        File assetsDir = new File(channelDir, "assets");
        assetsDir.mkdir();
        FileUtil.save(assetsDir.getAbsolutePath(), "g17173_init_config.json", channel.getConfig().getBytes());
    }

    private void createO17173File(Channel channel, String buildPath) {
        File channelDir = new File(buildPath, "channel-config");
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
