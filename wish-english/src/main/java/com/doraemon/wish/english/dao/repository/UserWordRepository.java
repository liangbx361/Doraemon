package com.doraemon.wish.english.dao.repository;

import com.doraemon.wish.english.dao.model.UserWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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