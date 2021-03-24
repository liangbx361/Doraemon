package com.doraemon.wish.pack.dao.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Table(name = "pack_game")
@DynamicUpdate
@Entity
@Data
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    /**
     * 游戏名称（中文）
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * 游戏编码（中文缩写+sy）
     */
    @Column(nullable = false, unique = true)
    private String code;

    /**
     * 发行分配的APP ID
     */
    @Column(nullable = false, unique = true)
    private String appId;

    /**
     * 发行分配的密钥
     */
    @Column(nullable = false)
    private String appSecret;

    /**
     * 对接类型
     * @see SdkIntegrationType
     */
    @Column(nullable = false)
    private String sdkIntegrationType;

    /**
     * 支持的渠道
     * Game放弃关系维护
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "game_id")
    private List<Channel> channels = new ArrayList<>();

    /**
     * 游戏母包
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    // 解决一个Model里包含两个FetchType.EAGER
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "game_id")
    private List<Apk> apks = new ArrayList<>();

    /**
     * 集成的事件采集器配置
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<GameHubEtp> etps = new ArrayList<>();

    /**
     * 集成的插件配置
     */
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<GameHubPlugin> plugins = new ArrayList<>();

}
