package com.doraemon.user.dao.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户名
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * 用户密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * 用户角色列表
     */
    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;
}
