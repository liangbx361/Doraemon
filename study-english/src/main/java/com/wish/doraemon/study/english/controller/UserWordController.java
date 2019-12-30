package com.wish.doraemon.study.english.controller;

import com.wish.doraemon.study.english.dao.model.UserWord;
import com.wish.doraemon.study.english.service.UserWordService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(StudyEnApiPath.USER_WORd)
public class UserWordController {

    private final UserWordService userWordService;

    public UserWordController(UserWordService userWordService) {
        this.userWordService = userWordService;
    }

    @PostMapping("")
    public UserWord create(@RequestBody UserWord userWord) {
        return userWordService.create(userWord);
    }

    @GetMapping("/{id}")
    public UserWord query(@PathVariable Integer id) {
        return userWordService.query(id);
    }

    @GetMapping("")
    public Page<UserWord> queryByPage(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return userWordService.queryByPage(pageNo, pageSize);
    }

    @PutMapping("/{id}")
    public UserWord update(@PathVariable Integer id, @RequestBody UserWord userWord) {
        return userWordService.update(id, userWord);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userWordService.delete(id);
    }

    @PostMapping("/{id}/review")
    public UserWord review(@PathVariable Integer id,
                           @RequestParam(name = "readLevel") Integer readLevel,
                           @RequestParam(name = "listenLevel") Integer listenLevel,
                           @RequestParam(name = "speakLevel") Integer speakLevel,
                           @RequestParam(name = "writeLevel") Integer writeLevel) {
        return userWordService.review(id, readLevel, listenLevel, speakLevel, writeLevel);
    }
}
