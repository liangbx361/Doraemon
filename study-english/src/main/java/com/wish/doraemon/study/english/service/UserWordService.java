package com.wish.doraemon.study.english.service;

import com.wish.doraemon.study.english.dao.model.UserWord;
import com.wish.doraemon.study.english.dao.repository.UserWordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class UserWordService {

    private final UserWordRepository repository;

    public UserWordService(UserWordRepository repository) {
        this.repository = repository;
    }

    public UserWord create(UserWord userWord) {
        return repository.save(userWord);
    }

    public UserWord query(Integer id) {
        Optional<UserWord> userWord = repository.findById(id);
        if (userWord.isPresent()) {
            return userWord.get();
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public Page<UserWord> queryByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAll(pageable);
    }

    public UserWord update(@PathVariable Integer id, UserWord userWord) {
        if (repository.existsById(id)) {
            return repository.save(userWord);
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}