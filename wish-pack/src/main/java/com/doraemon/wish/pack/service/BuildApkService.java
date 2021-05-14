package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.BuildApk;
import com.doraemon.wish.pack.dao.repository.BuildApkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BuildApkService {

    private final BuildApkRepository repository;

    public BuildApkService(BuildApkRepository repository) {
        this.repository = repository;
    }

    public Page<BuildApk> queryByPage(Long gameId, Long channelId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createTime").descending());
        if(channelId >=  0) {
            return repository.findByGameIdAndChannelId(gameId, channelId, pageable);
        } else {
            return repository.findByGameId(gameId, pageable);
        }
    }
}
