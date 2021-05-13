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
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class BuildApkTaskService {

    private BuildApkTaskRepository taskRepository;
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

    private LinkedBlockingQueue<BuildApkTask> mBuildApkTaskQueue;

    public BuildApkTaskService(BuildApkTaskRepository taskRepository, GameRepository gameRepository,
                               ChannelRepository channelRepository, PluginRepository pluginRepository,
                               BuildApkRepository buildApkRepository) {
        this.taskRepository = taskRepository;
        this.gameRepository = gameRepository;
        this.channelRepository = channelRepository;
        this.pluginRepository = pluginRepository;
        this.buildApkRepository = buildApkRepository;

        mBuildApkTaskQueue = new LinkedBlockingQueue<>();

        loadHistoryTask();
        runLoopThread();
    }

    private void loadHistoryTask() {
        List<BuildApkTask> buildingTasks = taskRepository.findAllByStatusEquals(BuildApkTask.Status.BUILDING);
        mBuildApkTaskQueue.addAll(buildingTasks);
        List<BuildApkTask> createTasks = taskRepository.findAllByStatusEquals(BuildApkTask.Status.CREATE);
        mBuildApkTaskQueue.addAll(createTasks);
    }

    public BuildApkTask create(BuildApkTask task) {
        task.setStatus(BuildApkTask.Status.CREATE);
        task.setCreateTime(new Date());
        task =  taskRepository.save(task);

        try {
            mBuildApkTaskQueue.put(task);
            System.out.println("mLoopThread -> add to queue");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return task;
    }

    public Page<BuildApkTask> queryByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        return taskRepository.findAll(pageable);
    }

    private void runLoopThread() {
        new Thread(() -> {
            while (true) {
                System.out.println("Build apk task -> loop");
                try {
                    BuildApkTask buildApkTask = mBuildApkTaskQueue.take();
                    System.out.println("mLoopThread -> runBuildTask");
                    runBuildTask(buildApkTask);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 1. 生成构建配置文件
     * 2. 构建母包
     * 3. 上传母包到指定位置
     */
    private void runBuildTask(BuildApkTask task) {
        updateBuildTaskStatus(task, BuildApkTask.Status.BUILDING);

        for (Long channelId : task.getChannelIds()) {
            cleanBuildDir(buildPath);

            Optional<Game> gameOptional = gameRepository.findById(task.getGameId());
            if(!gameOptional.isPresent()) {
                setFailStatus(task, "Game ID " + task.getGameId() + " 不存在");
                return;
            }
            Game game = gameOptional.get();

            Optional<Channel> channelOptional = channelRepository.findById(channelId);
            if(!channelOptional.isPresent()) {
                setFailStatus(task, "Channel ID " + channelId + " 不存在");
                return;
            }
            Channel channel = channelOptional.get();

            GameHubConfig gameHubConfig = GameHubUtil.toConfig(game);
            gameHubConfig.gopId = channel.getCode();
            gameHubConfig.gopClassName = channel.getClassName();

            PackConfig packConfig = new PackConfig();
            packConfig.game = game.getCode();
            packConfig.channel = channel.getCode();
            packConfig.type = channel.getType();
            packConfig.packageName = channel.getPackageName();

            PackPlugin packPlugin;
            try {
                packPlugin = JsonMapperUtil.stringToObject(channel.getPluginConfig(), PackPlugin.class);
            } catch (IOException e) {
                e.printStackTrace();
                setFailStatus(task, "channel " + channel.getName() + " 配置序列化失败");
                return;
            }

            for(PackPlugin.Item item : packPlugin.items) {
                Optional<Plugin> pluginOptional = pluginRepository.findById(item.pluginId);
                if(!pluginOptional.isPresent())  {
                    setFailStatus(task, "plugin id " + item.pluginId + " 不存在");
                    return;
                }
                Plugin plugin = pluginOptional.get();
                PluginVersion pluginVersion;
                if(item.versionId == -1) {
                    pluginVersion = PluginUtil.getLatestVersion(plugin);
                } else {
                    pluginVersion = PluginUtil.getVersion(plugin, item.versionId);
                }
                if(pluginVersion == null) {
                    setFailStatus(task, "plugin version " + item.versionId + " 不存在");
                    return;
                }
                item.name = pluginVersion.getFileName();
            }

            // 加入GameHub配置
            PackPlugin.Item gameHubItem = new PackPlugin.Item();
            gameHubItem.name = "gamehub-config";
            packPlugin.items.add(gameHubItem);

            // TODO 加入GameHub插件和ETP配置

            // 加入渠道配置
            PackPlugin.Item channelItem = new PackPlugin.Item();
            channelItem.name = "channel-config";
            packPlugin.items.add(channelItem);

            // 生成配置文件
            try {
                createGamehubConfigFile(gameHubConfig, buildPath);
            } catch (IOException e) {
                e.printStackTrace();
                setFailStatus(task, "gamehub-config生成失败");
                return;
            }

            try {
                createJsonFile(packConfig, "config.json");
            } catch (IOException e) {
                e.printStackTrace();
                setFailStatus(task, "config.json生成失败");
                return;
            }

            try {
                createJsonFile(packPlugin, "plugin.json");
            } catch (IOException e) {
                e.printStackTrace();
                setFailStatus(task, "plugin.json生成失败");
                return;
            }

            try {
                createChannelFile(channel, buildPath);
            } catch (IOException e) {
                e.printStackTrace();
                setFailStatus(task, "channel-config生成失败");
                return;
            }

            // 构建
            String filePath = apkPath + File.separator + task.getApkName();
            try {
                ShellUtil.exec("g17173-pack -m -apk " + filePath + " -c " + buildPath + " -pluginDir " + pluginDir);
                if(!G17173PackUtil.isModifySuccess()) {
                    setFailStatus(task, "构建失败");
                    return;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                setFailStatus(task, "构建失败");
                return;
            }

            // 查找母包是否存在
            File apkFile = findBuildApk();
            File deskApkDir = new File(buildApkPath, game.getId().toString());
            if (!deskApkDir.exists()) {
                deskApkDir.mkdirs();
            }
            File destApkFile = new File(deskApkDir, apkFile.getName());
            try {
                FileCopyUtils.copy(apkFile, destApkFile);
                task.getBuildApks().add(destApkFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
                setFailStatus(task, "文件拷贝失败");
                return;
            }

            // 保存构建的APk
            BuildApk buildApk = new BuildApk();
            buildApk.setGameId(game.getId());
            buildApk.setChannelId(channel.getId());
            buildApk.setCreateTime(new Date());
            buildApk.setApk(destApkFile.getName());
            buildApkRepository.save(buildApk);
        }

        // 任务成功
        task.setStatus(BuildApkTask.Status.SUCCESS);
        taskRepository.save(task);
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

    private void createJsonFile(Object model, String fileName) throws IOException {
        String modelJson = JsonMapperUtil.objectToString(model);
        FileUtil.save(buildPath, fileName, modelJson.getBytes());
    }

    private void createGamehubConfigFile(GameHubConfig config, String buildPath) throws IOException {
        File configDir = new File(buildPath, "gamehub-config");
        configDir.mkdir();
        File assetsDir = new File(configDir, "assets");
        assetsDir.mkdir();

        JsonUtil.modelToJsonFile(config, assetsDir.getAbsolutePath(), "gamehub_init_config.json");
    }

    private void createChannelFile(Channel channel, String buildPath) throws IOException {
        switch (channel.getCode()) {
            case "c17173":
                createC17173File(channel, buildPath);
                break;

            case "o17173":
                createO17173File(channel, buildPath);
                break;
        }
    }

    private void createC17173File(Channel channel, String buildPath) throws IOException {
        File channelDir = new File(buildPath, "channel-config");
        channelDir.mkdir();
        File assetsDir = new File(channelDir, "assets");
        assetsDir.mkdir();
        FileUtil.save(assetsDir.getAbsolutePath(), "g17173_init_config.json", channel.getConfig().getBytes());
    }

    private void createO17173File(Channel channel, String buildPath) throws IOException {
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

    private void updateBuildTaskStatus(BuildApkTask task, String status) {
        task.setStatus(status);
        taskRepository.save(task);
    }

    private void setFailStatus(BuildApkTask task, String failReason) {
        task.setStatus(BuildApkTask.Status.FAIL);
        task.setFailReason(failReason);
        taskRepository.save(task);
    }
}
