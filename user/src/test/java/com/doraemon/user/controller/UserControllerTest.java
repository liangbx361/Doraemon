package com.doraemon.user.controller;

import com.doraemon.test.CrudControllerTest;
import com.doraemon.user.dao.model.RoleEnum;
import com.doraemon.user.dao.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserControllerTest extends CrudControllerTest<User> {

    @Override
    protected User createModel() {
        User user = new User();
        user.setName("mike");
        user.setNickname("赤峰");
        user.setPassword("123456");
        List<String> roles = new ArrayList<>();
        roles.add(RoleEnum.ADMIN.value());
        roles.add(RoleEnum.STUDY_EN.value());
        user.setRoles(roles);
        return user;
    }

    @Override
    protected String getRestfulApiPath() {
        return UserApiPath.USER;
    }

    @Test
    @Override
    public void testCrud() throws Exception {
        executeCrud();
    }
}
