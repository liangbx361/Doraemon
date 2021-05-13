package com.doraemon.wish.pack.dao.repository;

import com.doraemon.wish.pack.dao.model.BuildApkTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildApkTaskRepository extends JpaRepository<BuildApkTask, Long> {

    Optional<BuildApkTask> findFirstByStatusEquals(String status);

    List<BuildApkTask> findAllByStatusEquals(String status);

}
