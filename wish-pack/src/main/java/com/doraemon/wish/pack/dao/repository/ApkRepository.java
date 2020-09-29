package com.doraemon.wish.pack.dao.repository;

import com.doraemon.wish.pack.dao.model.Apk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApkRepository extends JpaRepository<Apk, Long> {

    Page<Apk> findByGame_Id(Long gameId, Pageable pageable);
}