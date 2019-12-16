package com.wish.doraemon.study.english.service;

import com.wish.doraemon.study.english.dao.model.Word;
import com.wish.doraemon.study.english.dao.repository.WordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class WordService {

    private final WordRepository repository;

    public WordService(WordRepository repository) {
        this.repository = repository;
    }

    public Word create(Word word) {
        return repository.save(word);
    }

    public Word query(Integer id) {
        Optional<Word> word = repository.findById(id);
        if (word.isPresent()) {
            return word.get();
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public Page<Word> queryByPage(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAll(pageable);
    }

    public Word update(@PathVariable Integer id, Word word) {
        if (repository.existsById(id)) {
            return repository.save(word);
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}