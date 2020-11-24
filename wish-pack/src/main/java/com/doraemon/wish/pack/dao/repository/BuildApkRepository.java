package com.doraemon.wish.pack.dao.repository;

import com.doraemon.wish.pack.dao.model.BuildApk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildApkRepository extends JpaRepository<BuildApk, Long> {

    Page<BuildApk> findByGameIdAndChannelId(Long gameId, Long channelId,  Pageable pageable);

    Page<BuildApk> findByGameId(Long gameId, Pageable pageable);

}
