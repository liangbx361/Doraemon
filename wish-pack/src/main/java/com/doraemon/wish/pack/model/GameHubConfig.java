package com.doraemon.wish.pack.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class GameHubConfig {

    /**
     * 环境
     */
    public int env = 3;

    /**
     * GameHub APP ID
     */
    public String appId;

    /**
     * GameHub APP Secret
     */
    public String appSecret;

    /**
     * 联运编码
     */
    public String gopId;

    /**
     * 联运类名
     */
    public String gopClassName;

    /**
     * GameHub插件
     */
    public List<Plugin> plugins = new ArrayList<>();

    /**
     * GameHub数据上报平台
     */
    public List<EventTrackPlatform> etps = new ArrayList<>();

    /**
     * 是否开启调试模式
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean debug;

    public static class Plugin {

        public String className;

        public String param;
    }

    public static class EventTrackPlatform {

        public String className;

        public String param;
    }
}
