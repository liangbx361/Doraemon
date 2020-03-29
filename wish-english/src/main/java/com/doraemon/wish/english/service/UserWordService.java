package com.doraemon.wish.english.service;

import com.doraemon.wish.english.dao.model.UserWordRecord;
import com.doraemon.wish.english.dao.model.UserWord;
import com.doraemon.wish.english.dao.repository.UserWordRecordRepository;
import com.doraemon.wish.english.dao.repository.UserWordRepository;
import com.doraemon.user.util.StringUtil;
import com.doraemon.user.util.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        if (userWord.isPresent()) {
            Integer userId = UserUtil.getUserId(request);
            if (userId.equals(userWord.get().getUserId())) {
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

    public UserWord update(Integer id, UserWord userWord) {
        if (repository.existsById(id)) {
            return review(id, userWord.getReadLevel(), userWord.getListenLevel(), userWord.getSpeakLevel(), userWord.getWriteLevel());
        } else {
            throw new IllegalStateException("id not exit");
        }
    }

    public UserWord updateWord(Integer id, String pronounce, String chinese, String image, String example) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("id not exit");
        }

        UserWord userWord = query(id);

        if(StringUtil.isNotEmpty(pronounce)) {
            userWord.setPronounce(pronounce);
        }
        if(StringUtil.isNotEmpty(chinese)) {
            userWord.setChinese(chinese);
        }
        if(StringUtil.isNotEmpty(image)) {
            userWord.setImage(image);
        }
        if(StringUtil.isNotEmpty(example)) {
            userWord.setExample(example);
        }
        userWord.setWordUpdateTime(new Date());

        return repository.save(userWord);
    }

    public UserWord review(Integer id, Integer readLevel, Integer listenLevel, Integer speakLevel, Integer writeLevel) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("id not exit");
        }

        UserWord userWord = query(id);

        // 更新当前数据
        userWord.setReadLevel(readLevel);
        userWord.setListenLevel(listenLevel);
        userWord.setSpeakLevel(speakLevel);
        userWord.setWriteLevel(writeLevel);
        userWord.setLastReviewTime(new Date());
        userWord = repository.save(userWord);

        // 新增复习记录
        UserWordRecord record = new UserWordRecord();
        record.setUserWordId(userWord.getId());
        record.setCreateTime(userWord.getLastReviewTime());
        record.setListenLevel(userWord.getListenLevel());
        record.setReadLevel(userWord.getReadLevel());
        record.setSpeakLevel(userWord.getSpeakLevel());
        record.setWriteLevel(userWord.getWriteLevel());
        recordRepository.save(record);

        return userWord;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}