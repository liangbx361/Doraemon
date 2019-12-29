package com.wish.doraemon.study.english.service;

import com.wish.doraemon.study.english.dao.model.Word;
import com.wish.doraemon.study.english.dao.repository.WordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.Optional;

@Service
public class WordService {

    private final WordRepository repository;

    public WordService(WordRepository repository) {
        this.repository = repository;
    }

    public Word create(Word word) {
        Date date = new Date();
        word.setCreateTime(date);
        word.setUpdateTime(date);
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

    public Word query(String name) {
        Optional<Word> wordOptional = repository.findByWord(name);
        if (wordOptional.isPresent()) {
            return wordOptional.get();
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
            word.setUpdateTime(new Date());
            return repository.save(word);

//            word.setUpdateTime(new Date());
//            repository.update(word.getId(), word.getWord(), word.getPronounce(), word.getChinese(),
//                word.getExample(), word.getCategory(), word.getUpdateTime());
//            return word;
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}