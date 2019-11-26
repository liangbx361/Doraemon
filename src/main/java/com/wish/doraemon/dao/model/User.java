package com.wish.doraemon.dao.model;

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
    @GeneratedValue()
    private Integer id;

    /**
     * 用户名
     */
    @Column(nullable = false)
    private String name;

    /**
     * 用户密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * 用户角色列表
     */
    @ElementCollection
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
