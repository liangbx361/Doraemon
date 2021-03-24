package com.doraemon.wish.pack.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "pack_plugin")
@DynamicUpdate
@Entity
@Data
public class Plugin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    /**
     * 名称（同一个插件名称一样）
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * 插件类型
     */
    @Column(nullable = false)
    private String type;


    /**
     * 插件类名（可选）
     */
    @Column
    private String className;

    /**
     * 版本列表
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "plugin_id")
    private List<PluginVersion> versions = new ArrayList<>();
}
