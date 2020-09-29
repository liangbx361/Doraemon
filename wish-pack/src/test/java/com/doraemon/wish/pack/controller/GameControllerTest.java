package com.doraemon.wish.pack.controller;

import com.doraemon.test.CrudControllerTest;
import com.doraemon.wish.pack.dao.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest extends CrudControllerTest<Game> {

    @Override
    protected Game createModel() {
        Game game = new Game();
        game.setName("天龙手游");
        game.setCode("tlsy");
        return game;
    }

    @Override
    protected String getRestfulApiPath() {
        return "/api/v1/pack/game";
    }

    @Test
    @Override
    protected void testCrud() throws Exception {
        executeCrud();
    }
}