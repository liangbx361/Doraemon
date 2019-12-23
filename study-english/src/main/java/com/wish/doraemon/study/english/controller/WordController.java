package com.wish.doraemon.study.english.controller;

import com.wish.doraemon.study.english.dao.model.UserWord;
import com.wish.doraemon.study.english.dao.model.Word;
import com.wish.doraemon.study.english.service.UserWordService;
import com.wish.doraemon.study.english.service.WordService;
import com.wish.doraemon.user.dao.model.User;
import com.wish.doraemon.user.security.jwt.JwtHandler;
import com.wish.doraemon.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/study-en/words")
public class WordController {

    private final WordService wordService;
    private final UserService userService;
    private final UserWordService userWordService;
    private final HttpServletRequest request;

    public WordController(WordService wordService, UserService userService, UserWordService userWordService, HttpServletRequest request) {
        this.wordService = wordService;
        this.userService = userService;
        this.userWordService = userWordService;
        this.request = request;
    }

    @PostMapping("")
    public Word create(@RequestBody Word word) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.replace(JwtHandler.TOKEN_PREFIX, "");
        String username = JwtHandler.getInstance().getUsernameByToken(token);
        User user = userService.findUserByUserName(username);

        word = wordService.create(word);

        UserWord userWord = new UserWord();
        userWord.setUserId(user.getId());
        userWord.setWord(word.getWord());
        userWordService.create(userWord);

        return word;
    }

    @GetMapping("/{id}")
    public Word query(@PathVariable Integer id) {
        return wordService.query(id);
    }

    @GetMapping("")
    public Page<Word> queryByPage(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return wordService.queryByPage(pageNo, pageSize);
    }

    @PutMapping("/{id}")
    public Word update(@PathVariable Integer id, @RequestBody Word word) {
        return wordService.update(id, word);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        wordService.delete(id);
    }
}
