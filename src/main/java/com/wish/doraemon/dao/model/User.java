package com.wish.doraemon.dao.model;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class User {

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue()
    private String id;

    /**
     * 用户名
     */
    private String name;

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
