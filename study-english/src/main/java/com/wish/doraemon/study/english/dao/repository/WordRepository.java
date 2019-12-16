package com.wish.doraemon.study.english.dao.repository;

import com.wish.doraemon.study.english.dao.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Integer> {

}