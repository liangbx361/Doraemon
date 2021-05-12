package com.doraemon.wish.pack.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "pack_channel")
@DynamicUpdate
@Entity
@Data
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    /**
     * 渠道名称（中文）
     */
    @Column(nullable = false)
    private String name;

    /**
     * 渠道编码（同步发行后台数据）
     */
    @Column(nullable = false)
    private String code;

    /**
     * 渠道类名
     */
    @Column(nullable = false)
    private String className;

    /**
     * 渠道打包类型
     */
    @Column(nullable = false)
    private String type;

    /**
     * 渠道包名
     */
    @Column(nullable = false)
    private String packageName;

    /**
     * 渠道参数配置，使用JSON格式
     */
    @Column(length = 2048)
    private String config;

    /**
     * 关联的游戏
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;

    /**
     * 关联的联运平台
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private GameHubGop gop;

    /**
     * 渠道关联的插件ID
     */
    @Deprecated
    @ElementCollection(fetch = FetchType.EAGER)
    @Column()
    private List<Long> pluginIds = new ArrayList<>();

    /**
     * 渠道插件配置
     */
    @Column(length = 2048)
    private String pluginConfig;


}
