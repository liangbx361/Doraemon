package com.wish.doraemon.dao.model;

/**
 * 角色
 */
public enum RoleEnum {

    /**
     * 管理员
     */
    ADMIN("admin"),

    /**
     * 开发
     */
    DEV("dev"),

    /**
     * 测试
     */
    TEST("test");

    private String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
