package com.wish.doraemon.study.english.controller;

import com.wish.doraemon.study.english.dao.model.UserWord;
import com.wish.doraemon.test.CrudControllerTest;
import com.wish.doraemon.user.controller.UserApiPath;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class UserWordControllerTest extends CrudControllerTest<UserWord> {

    @Override
    protected void doLogin() throws Exception {
        super.doLogin();

        getMockMvc().perform(
            post(UserApiPath.ACCOUNT_LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"admin\", \"password\": \"123456\" }")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }

    @Override
    protected UserWord createModel() {
        UserWord userWord = new UserWord();
        userWord.setWord("hello");
        return userWord;
    }

    @Override
    protected String getRestfulApiPath() {
        return StudyEnApiPath.USER_WORd;
    }

    @Test
    @Override
    public void testCrud() throws Exception {
        executeCrud();
    }
}
