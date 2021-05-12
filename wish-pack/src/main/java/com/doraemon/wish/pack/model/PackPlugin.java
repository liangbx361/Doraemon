package com.doraemon.wish.pack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PackPlugin {

    /**
     * 指定要移除的类文件或目录
     */
    @JsonProperty("remove_classes")
    public List<String> removeClasses = new ArrayList<>();

    /**
     * 指定要移除的so文件
     */
    @JsonProperty("remove_libs")
    public List<String> removeLibs = new ArrayList<>();

    /**
     * 集成的插件列表
     */
    @JsonProperty("plugins")
    public List<Item> items = new ArrayList<>();

    /**
     * 指定仅保留的cpu架构类型
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String libFilter;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {

        public String name;

        public int classesIndex = 1;

        public Long pluginId;

        public Long versionId;

    }
}
