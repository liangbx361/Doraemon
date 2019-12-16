package com.wish.doraemon.study.english.dao.repository;

import com.wish.doraemon.study.english.dao.model.UserWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWordRepository extends JpaRepository<UserWord, Integer> {

}