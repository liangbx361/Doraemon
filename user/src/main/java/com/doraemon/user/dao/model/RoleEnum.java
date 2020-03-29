package com.doraemon.user.dao.model;

/**
 * 角色
 */
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN("admin"),

    /**
     * 英文学习
     */
    STUDY_EN("study-en");

    private String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
