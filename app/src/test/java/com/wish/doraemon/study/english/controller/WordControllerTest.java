package com.wish.doraemon.study.english.controller;

import com.wish.doraemon.study.english.dao.model.Word;
import com.wish.doraemon.test.CrudControllerTest;
import org.junit.jupiter.api.Test;

class WordControllerTest extends CrudControllerTest<Word> {

    @Override
    protected Word createModel() {
        Word word = new Word();
        word.setWord("hello");
        return word;
    }

    @Override
    protected String getRestApi() {
        return Api.WORD;
    }

    @Test
    @Override
    public void testCrud() throws Exception {
        executeCrud();
    }
}