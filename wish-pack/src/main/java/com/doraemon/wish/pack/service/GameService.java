package com.doraemon.wish.pack.service;

import com.doraemon.wish.pack.dao.model.Game;
import com.doraemon.wish.pack.dao.repository.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Game create(Game game) {
        return repository.save(game);
    }

    public Game query(Long id) {
        Optional<Game> game = repository.findById(id);
        if (game.isPresent()) {
            return game.get();
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public Page<Game> queryByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAll(pageable);
    }

    public Game update(@PathVariable Long id, Game game) {
        if (repository.existsById(id)) {
            game.setId(id);
            return repository.save(game);
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}