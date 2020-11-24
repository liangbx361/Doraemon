package com.doraemon.wish.pack.dao.repository;

import com.doraemon.wish.pack.dao.model.BuildChildApk;
import com.doraemon.wish.pack.dao.model.BuildChildApkTask;
import com.doraemon.wish.pack.dao.model.BuildTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildChildApkTaskRepository extends JpaRepository<BuildChildApkTask, Long> {

    Optional<BuildChildApkTask> findFirstByStatusEquals(String status);

}
