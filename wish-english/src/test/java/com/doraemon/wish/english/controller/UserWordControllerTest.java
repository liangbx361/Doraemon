package com.doraemon.wish.english.controller;//package com.doraemon.wish.english.controller;

import com.alibaba.fastjson.JSONObject;
import com.doraemon.test.CrudControllerTest;
import com.doraemon.wish.english.dao.model.UserWord;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

public class UserWordControllerTest extends CrudControllerTest<UserWord> {

    @Override
    protected UserWord createModel() {
        UserWord userWord = new UserWord();
        userWord.setUserId(getUser().getId());
        userWord.setWord("hello");
        return userWord;
    }

    @Override
    protected String getRestfulApiPath() {
        return EnglishApiPath.USER_WORd;
    }

    @Test
    @Override
    public void testCrud() throws Exception {
        executeCrud();
    }

    @Override
    protected void update(Integer id, UserWord model) throws Exception {
        model.setExample("hello world");
        getMockMvc().perform(patch(getRestfulApiPath() + "/" + id + "/word")
            .header("Authorization", getAuth())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(model)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
    }
}
