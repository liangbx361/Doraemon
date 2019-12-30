package com.wish.doraemon.study.english.dao.repository;

import com.wish.doraemon.study.english.dao.model.UserWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface UserWordRepository extends JpaRepository<UserWord, Integer> {

    Page<UserWord> findAllByUserId(Integer userId, Pageable pageable);

    //    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query("update UserWord w set "
//        + "w.level=:level, "
//        + "w.reviewCount=:reviewCount, "
//        + "w.lastReviewTime=:lastReviewTime "
//        + "where w.id=:id"
//    )
//    void update(@Param("id") Integer id,
//                @Param("level") int level,
//                @Param("reviewCount") int reviewCount,
//                @Param("lastReviewTime") Date lastReviewTime);
}