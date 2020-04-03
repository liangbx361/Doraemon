package com.doraemon.wish.english.controller;

import com.doraemon.wish.english.service.IseService;
import com.doraemon.wish.english.dao.model.VoiceEvaluation;
import com.doraemon.wish.english.dao.model.XunFeiEvaluation;
import com.droaemon.common.util.JsonMapperUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(EnglishApiPath.EVALUATION)
public class EvaluationController {

    private IseService iseService;

    public EvaluationController(IseService iseService) {
        this.iseService = iseService;
    }

    @PostMapping("/voice")
    public VoiceEvaluation voiceEvaluation(@RequestParam("voiceFile") MultipartFile voiceFile) throws IOException {
        String resultJson = iseService.ise(voiceFile.getBytes());
        XunFeiEvaluation evaluation = JsonMapperUtil.stringToObject(resultJson, XunFeiEvaluation.class);

        if(evaluation.code.equals("0")) {
            int score = evaluation.data.read_word.rec_paper.read_word.total_score;
            int level;
            if(score > 80) {
                level = 3;
            } else if(score > 60) {
                level = 2;
            } else {
                level = 1;
            }
            VoiceEvaluation voiceEvaluation = new VoiceEvaluation();
            voiceEvaluation.setLevel(level);
            return voiceEvaluation;
        }

        throw new IllegalStateException("evaluation fail");
    }
}
