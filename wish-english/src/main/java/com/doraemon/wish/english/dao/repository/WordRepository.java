package com.doraemon.wish.english.dao.repository;

import com.doraemon.wish.english.dao.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordRepository extends JpaRepository<Word, Integer> {

    Optional<Word> findByWord(String word);

//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query("update Word w set " +
//        "w.word=:word, " +
//        "w.pronounce=:pronounce, " +
//        "w.chinese=:chinese, " +
//        "w.example=:example, " +
//        "w.category=:category, " +
//        "w.updateTime=:updateTime" +
//        " where w.id=:id"
//    )
//    void update(@Param("id") Integer id,
//                @Param("word") String word,
//                @Param("pronounce") String pronounce,
//                @Param("chinese") String chinese,
//                @Param("example") String example,
//                @Param("category") String category,
//                @Param("updateTime") Date updateTime);
}