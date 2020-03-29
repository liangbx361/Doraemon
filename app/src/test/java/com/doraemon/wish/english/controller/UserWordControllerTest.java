//package com.doraemon.wish.english.controller;
//
//import com.doraemon.wish.english.dao.model.UserWord;
//import com.doraemon.test.CrudControllerTest;
//import com.doraemon.user.controller.UserApiPath;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//public class UserWordControllerTest extends CrudControllerTest<UserWord> {
//
//    @Override
//    protected void login() throws Exception {
//        super.login();
//
//        getMockMvc().perform(
//            post(UserApiPath.ACCOUNT_LOGIN)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"username\": \"admin\", \"password\": \"123456\" }")
//        )
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andReturn();
//    }
//
//    @Override
//    protected UserWord createModel() {
//        UserWord userWord = new UserWord();
//        userWord.setWord("hello");
//        return userWord;
//    }
//
//    @Override
//    protected String getRestfulApiPath() {
//        return StudyEnApiPath.USER_WORd;
//    }
//
//    @Test
//    @Override
//    public void testCrud() throws Exception {
//        executeCrud();
//    }
//}
