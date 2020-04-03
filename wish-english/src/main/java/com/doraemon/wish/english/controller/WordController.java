//package com.wish.doraemon.study.english.controller;
//
//import com.wish.doraemon.study.english.dao.model.Word;
//import com.wish.doraemon.study.english.service.WordService;
//import org.springframework.data.domain.Page;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(EnglishApiPath.WORD)
//public class WordController {
//
//    private final WordService wordService;
//
//    public WordController(WordService wordService) {
//        this.wordService = wordService;
//    }
//
//    @PostMapping("")
//    public Word createWord(@RequestBody Word word) {
//        word = wordService.create(word);
//        return word;
//    }
//
//    @GetMapping("/{id}")
//    public Word queryWord(@PathVariable Integer id) {
//        return wordService.query(id);
//    }
//
//    @GetMapping("")
//    public Page<Word> queryByPage(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
//                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
//        return wordService.queryByPage(pageNo, pageSize);
//    }
//
//    @PutMapping("/{id}")
//    public Word updateWord(@PathVariable Integer id, @RequestBody Word word) {
//        return wordService.update(id, word);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteWord(@PathVariable Integer id) {
//        wordService.delete(id);
//    }
//}
