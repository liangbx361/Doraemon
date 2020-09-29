package com.doraemon.wish.pack.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "pack_channel")
@DynamicUpdate
@Entity
@Data
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
    @Column(nullable = true)
    private String config;

    /**
     * 关联的游戏
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;

    /**
     * 渠道关联的插件ID
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Column()
    private List<Long> pluginIds = new ArrayList<>();

//    /**
//     * 渠道关联的插件
//     */
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Plugin.class)
//    // 关系表
//    @JoinTable(name = "pack_channel_plugin",
//        // 当前表参与的外键
//        joinColumns = {@JoinColumn(name = "channel_id", referencedColumnName = "id")},
//        // 关联表参与的外键
//        inverseJoinColumns = {@JoinColumn(name = "plugin_id", referencedColumnName = "id")}
//    )
//    private List<Plugin> plugins = new ArrayList<>();

}
