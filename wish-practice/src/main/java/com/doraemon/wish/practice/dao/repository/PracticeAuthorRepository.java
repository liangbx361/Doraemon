package com.doraemon.wish.practice.dao.repository;

import com.doraemon.wish.practice.dao.model.PracticeAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeAuthorRepository extends JpaRepository<PracticeAuthor, Long> {
    
}
