package com.doraemon.wish.pack.dao.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class GameHubEtp implements Serializable {

    /**
     * 分类
     */
    private String type;

    /**
     * 关联的插件类名
     */
    private String className;

    /**
     * 指定插件版本（默认指定最新版本）
     */
    private Long versionId = -1L;

    /**
     * 指定插件分包的位置
     */
    private int classesIndex = 1;

    /**
     * 参数
     */
    private String params;

}
