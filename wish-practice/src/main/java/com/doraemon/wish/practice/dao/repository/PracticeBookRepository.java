package com.doraemon.wish.practice.dao.repository;

import com.doraemon.wish.practice.dao.model.PracticeBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeBookRepository extends JpaRepository<PracticeBook, Long> {

}
