package com.doraemon.wish.english.util;

class AudioUtilsTest {

    @org.junit.jupiter.api.Test
    void wavToMp3() {
        String waveFile = "/Users/lbx/Music/word/look.wav";
        String mp3File = "look.mp3";

        AudioUtils.wavToMp3(waveFile, mp3File);
    }
}