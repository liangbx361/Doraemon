package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.BuildChildApk;
import com.doraemon.wish.pack.dao.repository.BuildChildApkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BuildChildApkService {

    private final BuildChildApkRepository buildChildApkRepository;

    public BuildChildApkService(BuildChildApkRepository buildChildApkRepository) {
        this.buildChildApkRepository = buildChildApkRepository;
    }

    public Page<BuildChildApk> queryByPage(Long gameId, Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return buildChildApkRepository.findByGameId(gameId, pageable);
    }
}
