package com.doraemon.wish.pack.dao.repository;

import com.doraemon.wish.pack.dao.model.BuildChildApk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildChildApkRepository extends JpaRepository<BuildChildApk, Long> {

    Page<BuildChildApk> findByGameId(Long gameId, Pageable pageable);

}
