package com.doraemon.wish.pack.util;

import com.doraemon.wish.pack.dao.model.Game;
import com.doraemon.wish.pack.dao.model.GameHubEtp;
import com.doraemon.wish.pack.dao.model.GameHubPlugin;
import com.doraemon.wish.pack.model.GameHubConfig;

import java.util.ArrayList;

public class GameHubUtil {

    public static GameHubConfig toConfig(Game game) {
        GameHubConfig config = new GameHubConfig();
        config.env = game.getEnv();
        config.appId = game.getAppId();
        config.appSecret = game.getAppSecret();
        config.debug = game.getDebug();

        config.plugins = new ArrayList<>();
        for(GameHubPlugin gameHubPlugin : game.getPlugins()) {
            GameHubConfig.Plugin plugin = new GameHubConfig.Plugin();
            plugin.className = gameHubPlugin.getClassName();
            plugin.param = gameHubPlugin.getParams();
            config.plugins.add(plugin);
        }

        config.etps = new ArrayList<>();
        for(GameHubEtp gameHubEtp : game.getEtps()) {
            GameHubConfig.EventTrackPlatform etp = new GameHubConfig.EventTrackPlatform();
            etp.className = gameHubEtp.getClassName();
            etp.param = gameHubEtp.getParams();
            config.etps.add(etp);
        }

        return config;
    }
}
