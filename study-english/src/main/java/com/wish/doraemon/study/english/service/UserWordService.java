package com.wish.doraemon.study.english.service;

import com.wish.doraemon.study.english.dao.model.UserWord;
import com.wish.doraemon.study.english.dao.model.UserWordRecord;
import com.wish.doraemon.study.english.dao.repository.UserWordRecordRepository;
import com.wish.doraemon.study.english.dao.repository.UserWordRepository;
import com.wish.doraemon.user.util.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class UserWordService {

    private final UserWordRepository repository;
    private final UserWordRecordRepository recordRepository;
    private final HttpServletRequest request;

    public UserWordService(UserWordRepository repository, UserWordRecordRepository recordRepository, HttpServletRequest request) {
        this.repository = repository;
        this.recordRepository = recordRepository;
        this.request = request;
    }

    public UserWord create(UserWord userWord) {
        Integer userId = UserUtil.getUserId(request);
        userWord.setUserId(userId);
        userWord.setCreateTime(new Date());

        return repository.save(userWord);
    }

    public UserWord query(Integer id) {
        Optional<UserWord> userWord = repository.findById(id);
        if(userWord.isPresent()) {
            Integer userId = UserUtil.getUserId(request);
            if(userId.equals(userWord.get().getUserId())) {
                return userWord.get();
            } else {
                throw new IllegalStateException("not your word!");
            }
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public Page<UserWord> queryByPage(Integer pageNo, Integer pageSize) {
        Integer userId = UserUtil.getUserId(request);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAllByUserId(userId, pageable);
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

    public UserWord review(Integer id, Integer readLevel, Integer listenLevel, Integer speakLevel, Integer writeLevel) {
        UserWord userWord = query(id);

        // 更新当前数据
        userWord.setReadLevel(readLevel);
        userWord.setListenLevel(listenLevel);
        userWord.setSpeakLevel(speakLevel);
        userWord.setWriteLevel(writeLevel);
        userWord.setLastReviewTime(new Date());
        userWord = repository.save(userWord);

        // 添加记录
        UserWordRecord record = new UserWordRecord();
        record.setUserWordId(userWord.getWordId());
        record.setCreateTime(userWord.getLastReviewTime());
        record.setListenLevel(userWord.getListenLevel());
        record.setReadLevel(userWord.getReadLevel());
        record.setSpeakLevel(userWord.getSpeakLevel());
        record.setWriteLevel(userWord.getWriteLevel());
        recordRepository.save(record);

        return userWord;
    }
}