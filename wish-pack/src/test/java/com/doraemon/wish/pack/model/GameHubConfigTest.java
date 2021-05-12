package com.doraemon.wish.pack.model;

import com.doraemon.wish.pack.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameHubConfigTest {

    @Test
    public void testNullValue() {
        GameHubConfig gameHubConfig = new GameHubConfig();
        gameHubConfig.env = 3;
        gameHubConfig.gopId = "g17173";
        gameHubConfig.gopClassName = "com.u17173.g17173";
        gameHubConfig.appId = "1";
        gameHubConfig.appSecret = "2";

        try {
            JsonUtil.modelToJsonFile(gameHubConfig, "out", "gamehub_init_config.json");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}