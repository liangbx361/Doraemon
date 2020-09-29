package com.doraemon.wish.pack.dao.repository;

import com.doraemon.wish.pack.dao.model.Plugin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PluginRepository extends JpaRepository<Plugin, Long> {

}