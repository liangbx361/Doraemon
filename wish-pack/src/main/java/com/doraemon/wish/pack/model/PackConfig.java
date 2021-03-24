package com.doraemon.wish.pack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PackConfig {

    /**
     * 游戏（编码）
     * 编码规则：游戏缩写+sy
     */
    public String game;

    /**
     * 渠道（编码）
     */
    public String channel;

    /**
     * 打包类型
     */
    public String type;

    /**
     * 包名
     */
    public String packageName;

    /**
     * 最低SDK版本号
     */
    public int minSdkVersion = -1;

    /**
     * 目标SDK版本号
     */
    public int targetSdkVersion = -1;

    /**
     * 版本号
     */
    public int versionCode = -1;

    /**
     * 版本名
     */
    public String versionName = null;

    /**
     * 是否启用MTP加固
     */
    public boolean mtp = false;

    /**
     * MTP游戏ID
     */
    public String mtpGameId;

    /**
     * MTP游戏证书
     */
    public String mtpCert;

    /**
     * MTP配置
     */
    public String mtpConfig;
}
