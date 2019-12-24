package com.wish.doraemon.study.english.dao.repository;

import com.wish.doraemon.study.english.dao.model.Word;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Integer> {

    Optional<Word> findByWord(String word);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Word w set " +
        "w.word=:word, " +
        "w.pronounce=:pronounce, " +
        "w.chinese=:chinese, " +
        "w.example=:example, " +
        "w.category=:category, " +
        "w.updateTime=:updateTime" +
        " where w.id=:id"
    )
    void updateWord(@Param("id") Integer id,
                    @Param("word") String word,
                    @Param("pronounce") String pronounce,
                    @Param("chinese") String chinese,
                    @Param("example") String example,
                    @Param("category") String category,
                    @Param("updateTime") Date updateTime);
}