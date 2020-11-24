package com.doraemon.wish.pack.dao.repository;

import com.doraemon.wish.pack.dao.model.BuildTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildTaskRepository extends JpaRepository<BuildTask, Long> {

    Optional<BuildTask> findFirstByStatusEquals(String status);

}
