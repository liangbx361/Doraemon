package com.doraemon.wish.pack.dao.model;

public interface PluginType {

    String GAMEHUB = "gamehub";

    /*
     * gamehub支持的插件
     */
    String GAMEHUB_PLUGIN = "gamehub-plugin";

    /**
     * gamehub支持的事件采集器
     */
    String GAMEHUB_ETP = "gamehub-etp";

    /**
     * gamehub支持的联运平台
     */
    String GAMEHUB_GOP = "gamehub-gop";

    /**
     * 17173联运平台自有的插件
     */
    String GOP_C17173 = "gop-c17173";

    /**
     * 17173海外联调平台自有的插件
     */
    String GOP_O17173 = "gop-o17173";

    /**
     * 游戏定制化的插件
     */
    String GAME = "game";
}
