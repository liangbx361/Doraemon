package com.wish.doraemon.study.english.dao.repository;

import com.wish.doraemon.study.english.dao.model.UserWordRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWordRecordRepository extends JpaRepository<UserWordRecord, Integer> {

}
