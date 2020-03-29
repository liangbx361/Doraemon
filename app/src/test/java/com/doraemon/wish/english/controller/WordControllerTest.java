package com.doraemon.wish.english.controller;

import com.doraemon.wish.english.dao.model.Word;
import com.doraemon.test.CrudControllerTest;
import org.junit.jupiter.api.Test;

class WordControllerTest extends CrudControllerTest<Word> {

    @Override
    protected Word createModel() {
        Word word = new Word();
        word.setWord("hello");
        return word;
    }

    @Override
    protected String getRestfulApiPath() {
        return StudyEnApiPath.WORD;
    }

    @Test
    @Override
    public void testCrud() throws Exception {
        executeCrud();
    }
}