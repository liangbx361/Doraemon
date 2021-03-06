package com.doraemon.wish.english.util;

import java.io.File;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

public class AudioUtils {

    public static boolean wavToMp3(String inPath, String outFile) {
        boolean status = false;
        File file = new File(inPath);
        try {
            execute(file, outFile);
            status = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 执行转化
     *
     * @param source      输入文件
     * @param desFileName 目标文件名
     * @return 转换之后文件
     */
    public static File execute(File source, String desFileName)
        throws Exception {
        File target = new File(desFileName);
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(new Integer(36000));
        audio.setChannels(new Integer(2));
        audio.setSamplingRate(new Integer(44100));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);
        return target;
    }
}