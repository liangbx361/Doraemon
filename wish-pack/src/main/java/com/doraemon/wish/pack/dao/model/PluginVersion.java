package com.doraemon.wish.pack.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 插件版本
 */
@Table(name = "pack_plugin_version")
@DynamicUpdate
@Entity
@Data
public class PluginVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    /**
     * 关联的插件
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plugin_id")
    @JsonIgnore
    private Plugin plugin;

    /**
     * 文件名
     */
    @Column(nullable = false)
    private String fileName;

    /**
     * 版本号
     */
    @Column(nullable = false)
    private String version;

    /**
     * 描述
     */
    @Column()
    private String description;

}
