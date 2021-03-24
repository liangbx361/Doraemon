package com.doraemon.wish.pack.controller;

import com.doraemon.wish.pack.dao.model.*;
import com.doraemon.wish.pack.service.*;
import com.droaemon.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pack/games")
public class GameController {

    private final HttpServletRequest request;
    private final GameService gameService;
    private final ChannelService channelService;
    private final ApkService apkService;
    private final BuildApkService buildApkService;
    private final BuildChildApkService buildChildApkService;

    @Value("${pack.apk-path}")
    private String apkPath;

    public GameController(HttpServletRequest request, GameService gameService, ChannelService channelService, ApkService apkService, BuildApkService buildApkService, BuildChildApkService buildChildApkService) {
        this.request = request;
        this.gameService = gameService;
        this.channelService = channelService;
        this.apkService = apkService;
        this.buildApkService = buildApkService;
        this.buildChildApkService = buildChildApkService;
    }

    @PostMapping("")
    public Game createGame(@RequestBody Game game) {
        return gameService.create(game);
    }

    @GetMapping("/{id}")
    public Game queryGame(@PathVariable Long id) {
        return gameService.query(id);
    }

    @GetMapping("")
    public Page<Game> queryGameByPage(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return gameService.queryByPage(pageNo, pageSize);
    }

    @PatchMapping("/{id}")
    public Game updateGameInfo(@PathVariable Long id,
                               @RequestBody Game game) {
        Game oldGame = gameService.query(id);
        if (StringUtil.isNotEmpty(game.getName())) {
            oldGame.setName(game.getName());
        }
        if (StringUtil.isNotEmpty(game.getCode())) {
            oldGame.setCode(game.getCode());
        }
        if(StringUtil.isNotEmpty(game.getAppId())) {
            oldGame.setAppId(game.getAppId());
        }
        if(StringUtil.isNotEmpty(game.getAppSecret())) {
            oldGame.setAppSecret(game.getAppSecret());
        }
        if(StringUtil.isNotEmpty(game.getSdkIntegrationType())) {
            oldGame.setSdkIntegrationType(game.getSdkIntegrationType());
        }
        if(game.getEtps() != null) {
            oldGame.setEtps(game.getEtps());
        }
        if(game.getPlugins() != null) {
            oldGame.setPlugins(game.getPlugins());
        }

        oldGame = gameService.update(id, oldGame);
        return oldGame;
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        List<Channel> channels = null;
        List<Apk> apks = null;

        Game game = gameService.query(id);
        if (!game.getChannels().isEmpty()) {
            channels = new ArrayList<>(game.getChannels());
            game.getChannels().clear();
        }
        if (!game.getApks().isEmpty()) {
            apks = new ArrayList<>(game.getApks());
            game.getChannels().clear();
        }
        gameService.update(id, game);
        gameService.delete(id);

        if (channels != null) {
            for (Channel item : channels) {
                channelService.delete(item.getId());
            }
        }

        if (apks != null) {
            for (Apk apk : apks) {
                apkService.delete(apk.getId());
            }
        }
    }

    @GetMapping("/{gameId}/apk")
    public Page<Apk> queryApkByPage(@PathVariable Long gameId,
                                    @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return apkService.queryByPage(gameId, pageNo, pageSize);
    }

    @PostMapping("/{gameId}/apk")
    public Game createApk(@PathVariable Long gameId, @RequestBody Apk apk) {
        Game game = gameService.query(gameId);

        apk.setCreateTime(new Date());
        game.getApks().add(apk);
        game = gameService.update(gameId, game);

        return game;
    }

    @DeleteMapping("/{gameId}/apk/{apkId}")
    public void deleteApk(@PathVariable Long gameId,
                          @PathVariable Long apkId) {
        Game game = gameService.query(gameId);
        Apk deleteApk = null;
        for (Apk item : game.getApks()) {
            if (item.getId().equals(apkId)) {
                deleteApk = item;
                break;
            }
        }
        if (deleteApk == null) {
            throw new IllegalArgumentException("apk not exit");
        }
        game.getApks().remove(deleteApk);
        gameService.update(gameId, game);
        apkService.delete(deleteApk.getId());
    }

    @PostMapping("/{id}/channel")
    public Game createChannel(@PathVariable Long id, @RequestBody Channel channel) {
        Game game = gameService.query(id);

        game.getChannels().add(channel);
        game = gameService.update(id, game);

        return game;
    }

    @GetMapping("/{gameId}/channel")
    public Page<Channel> queryChannelByPage(@PathVariable Long gameId,
                                            @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return channelService.queryByPage(gameId, pageNo, pageSize);
    }

    @GetMapping("/{gameId}/channel/{channelId}")
    public Channel queryChannel(@PathVariable Long gameId,
                                @PathVariable Long channelId) {
        return channelService.query(channelId);
    }

    @PutMapping("/{gameId}/channel/{channelId}")
    public Channel updateChannel(@PathVariable Long gameId,
                                 @PathVariable Long channelId,
                                 @RequestBody Channel newChannel) {
        Channel oldChannel = channelService.query(channelId);
        if (StringUtil.isNotEmpty(newChannel.getName())) {
            oldChannel.setName(newChannel.getName());
        }
        if (StringUtil.isNotEmpty(newChannel.getCode())) {
            oldChannel.setCode(newChannel.getCode());
        }
        if (StringUtil.isNotEmpty(newChannel.getConfig())) {
            oldChannel.setConfig(newChannel.getConfig());
        }
        if (StringUtil.isNotEmpty(newChannel.getPackageName())) {
            oldChannel.setPackageName(newChannel.getPackageName());
        }
        if (StringUtil.isNotEmpty(newChannel.getType())) {
            oldChannel.setType(newChannel.getType());
        }
        if(StringUtil.isNotEmpty(newChannel.getPluginConfig())) {
            oldChannel.setPluginConfig(newChannel.getPluginConfig());
        }
        if (newChannel.getPluginIds() != null) {
            System.out.println(newChannel.getPluginIds().size());
            oldChannel.setPluginIds(newChannel.getPluginIds());
        }
        return channelService.update(channelId, oldChannel);
    }

    @DeleteMapping("/{gameId}/channel/{channelId}")
    public void deleteChannel(@PathVariable Long gameId,
                              @PathVariable Long channelId) {
        Game game = gameService.query(gameId);
        Channel deleteChannel = null;
        for (Channel item : game.getChannels()) {
            if (item.getId().equals(channelId)) {
                deleteChannel = item;
                break;
            }
        }
        if (deleteChannel == null) {
            throw new IllegalArgumentException("channel not exit");
        }
        game.getChannels().remove(deleteChannel);
        gameService.update(gameId, game);
        channelService.delete(channelId);
    }

    @GetMapping("/{gameId}/build-apk")
    public Page<BuildApk> queryBuildApkByPage(@PathVariable Long gameId,
                                              @RequestParam(name = "channelId", required = false) Long channelId,
                                              @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        return buildApkService.queryByPage(gameId, channelId, pageNo, pageSize);
    }

    @GetMapping("/{gameId}/build-child-apk")
    public Page<BuildChildApk> queryBuildChildApkByPage(@PathVariable Long gameId,
                                                        @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<BuildChildApk> page = buildChildApkService.queryByPage(gameId, pageNo, pageSize);

        for(BuildChildApk item : page.getContent()) {
            String apkUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/doraemon/" + item.getApk();
            item.setApk(apkUrl);
        }

        return page;
    }
}
