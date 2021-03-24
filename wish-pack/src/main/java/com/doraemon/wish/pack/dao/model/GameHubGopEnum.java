package com.doraemon.wish.pack.dao.model;

public enum GameHubGopEnum {
    g173("com.u17173.gamehub.gop.g173.G173Gop"),
    og173("com.u17173.gamehub.gop.og173.OG173Gop");

    private String className;

    GameHubGopEnum(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
