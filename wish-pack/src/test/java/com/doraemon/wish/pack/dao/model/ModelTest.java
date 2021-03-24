package com.doraemon.wish.pack.dao.model;

import com.doraemon.test.BaseTest;
import com.doraemon.wish.pack.dao.repository.*;
import org.hibernate.annotations.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelTest extends BaseTest {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ApkRepository apkRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private PluginRepository pluginRepository;
    @Autowired
    private PluginVersionRepository pluginVersionRepository;

    @BeforeEach
    void setUp() {
        gameRepository.deleteAll();
        apkRepository.deleteAll();
        channelRepository.deleteAll();
        pluginRepository.deleteAll();
        pluginVersionRepository.deleteAll();
    }

    @Test
    public void testJsonField() {
        Game game = new Game();
        game.setName("测试");
        game.setCode("test");

        GameHubPlugin gameHubPlugin = new GameHubPlugin();
        gameHubPlugin.setParams("1");
        gameHubPlugin.setClassName("com.u17173");
        List<GameHubPlugin> plugins = new ArrayList<>();
        plugins.add(gameHubPlugin);
        game.setPlugins(plugins);

        game = gameRepository.save(game);
        gameRepository.delete(game);
    }

    @Test
    public void test() {
        // 新增游戏
        Game game = new Game();
        game.setName("天龙八部荣耀版");
        game.setCode("tlsy");
        gameRepository.save(game);

        // 游戏新增APK
        Apk apk = new Apk();
        apk.setCreateTime(new Date());
        apk.setFileName("tlsy_1.0.0.apk");
        game.getApks().add(apk);
        gameRepository.save(game);

        // 游戏新增渠道
        Channel channel = new Channel();
        channel.setName("17173");
        channel.setCode("c17173");
        channel.setType("ob");
        channel.setPackageName("com.cy.tlsy");
        game.getChannels().add(channel);
        gameRepository.save(game);

        System.out.println(game.getId());

        // 更新渠道参数配置
        game = gameRepository.findById(game.getId()).get();
        channel = game.getChannels().get(0);
        channel.setConfig("json");
        channelRepository.save(channel);

        // 新增插件
        Plugin plugin = new Plugin();
        plugin.setName("g17173");
        pluginRepository.save(plugin);

//        // 渠道添加插件
//        channel.getPlugins().add(plugin);
//        channelRepository.save(channel);

        // 添加插件版本
        PluginVersion version = new PluginVersion();
        version.setFileName("g17173-1.0.0");
        version.setVersion("1.0.0");
        plugin.getVersions().add(version);
        pluginRepository.save(plugin);

        // 删除插件版本
        plugin = pluginRepository.findById(plugin.getId()).get();
        List<PluginVersion> versions = new ArrayList<>(plugin.getVersions());
        plugin.getVersions().clear();
        pluginRepository.save(plugin);
        for(PluginVersion item : versions) {
            pluginVersionRepository.deleteById(item.getId());
        }

        // 删除渠道
        List<Channel> channels = new ArrayList<>(game.getChannels());
        game.getChannels().clear();
        gameRepository.save(game);
        for(Channel item : channels) {
            channelRepository.deleteById(item.getId());
        }
    }
}
