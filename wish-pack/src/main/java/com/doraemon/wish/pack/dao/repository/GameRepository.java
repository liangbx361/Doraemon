package com.doraemon.wish.pack.dao.repository;

import com.doraemon.wish.pack.dao.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

}