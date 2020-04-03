package com.doraemon.wish.english.controller;

import com.doraemon.wish.english.dao.model.UserWord;
import com.doraemon.wish.english.service.UserWordService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EnglishApiPath.USER_WORd)
public class UserWordController {

    private final UserWordService userWordService;

    public UserWordController(@Lazy UserWordService userWordService) {
        this.userWordService = userWordService;
    }

    @PostMapping("")
    public UserWord createUserWord(@RequestBody UserWord userWord) {
        return userWordService.create(userWord);
    }

    @GetMapping("/{id}")
    public UserWord queryUserWord(@PathVariable Integer id) {
        return userWordService.query(id);
    }

    @GetMapping("")
    public Page<UserWord> queryUserWords(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return userWordService.queryByPage(pageNo, pageSize);
    }

    @PatchMapping("/{id}/word")
    public UserWord updateUserWord(@PathVariable Integer id,
                                   @RequestBody UserWord userWord) {
        return userWordService.updateWord(id, userWord.getPronounce(), userWord.getChinese(), userWord.getImage(),
            userWord.getExample());
    }

    @PatchMapping("/{id}/level")
    public UserWord updateUserWordLevel(@PathVariable Integer id,
                                        @RequestParam(name = "readLevel") Integer readLevel,
                                        @RequestParam(name = "listenLevel") Integer listenLevel,
                                        @RequestParam(name = "speakLevel") Integer speakLevel,
                                        @RequestParam(name = "writeLevel") Integer writeLevel) {
        return userWordService.review(id, readLevel, listenLevel, speakLevel, writeLevel);
    }

    @DeleteMapping("/{id}")
    public void deleteUserWord(@PathVariable Integer id) {
        userWordService.delete(id);
    }
}
