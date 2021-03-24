package com.doraemon.wish.pack.dao.model;

import com.doraemon.test.BaseTest;
import com.doraemon.wish.pack.dao.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GameTest extends BaseTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void testGame() {
        Game game = new Game();
        game.setName("测试");
        game.setCode("test");
        game.setAppId("a");
        game.setAppSecret("b");
        game.setSdkIntegrationType(SdkIntegrationType.GAMEHUB);

        GameHubPlugin gameHubPlugin = new GameHubPlugin();
        gameHubPlugin.setParams("1");
        gameHubPlugin.setClassName("com.u17173");
        List<GameHubPlugin> plugins = new ArrayList<>();
        plugins.add(gameHubPlugin);
        game.setPlugins(plugins);

        game = gameRepository.save(game);
        gameRepository.delete(game);
    }
}
