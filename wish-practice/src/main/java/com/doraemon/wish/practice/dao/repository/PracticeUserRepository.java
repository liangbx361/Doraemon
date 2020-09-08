package com.doraemon.wish.practice.dao.repository;

import com.doraemon.wish.practice.dao.model.PracticeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeUserRepository extends JpaRepository<PracticeUser, Long> {

}
