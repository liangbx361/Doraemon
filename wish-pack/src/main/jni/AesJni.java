package com.doraemon.wish.pack;

public class AesJni {
    static {
        System.loadLibrary("aes-lib");
    }

    private native String encrypt(String plaintext);
}