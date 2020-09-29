package com.doraemon.wish.pack.dao.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "pack_game")
@DynamicUpdate
@Entity
@Data
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
}
