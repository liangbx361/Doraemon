package com.doraemon.wish.pack.dao.model;

public enum  GameHubPluginEnum {
    AIHELP("com.u17173.gamehub.plugin.AIHelpPlugin"),
    BUGLY("com.u17173.gamehub.plugin.bugly.BuglyPlugin"),
    FCM("com.u17173.gamehub.plugin.fcm.FcmPlugin"),
    SOCIAL("com.u17173.gamehub.plugin.social.SocialPluginImpl"),
    STRAWBERRY("com.u17173.gamehub.plugin.strawberry"),
    TPNS("com.u17173.gamehub.plugin.tpns.TpnsPushPlugin"),
    UPDATE("com.u17173.gamehub.plugin.update.UpdatePlugin");

    private String className;

    GameHubPluginEnum(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
