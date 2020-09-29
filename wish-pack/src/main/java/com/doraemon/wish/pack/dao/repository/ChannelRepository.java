package com.doraemon.wish.pack.dao.repository;

import com.doraemon.wish.pack.dao.model.Channel;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Page<Channel> findByGame_Id(Long gameId, Pageable pageable);

}