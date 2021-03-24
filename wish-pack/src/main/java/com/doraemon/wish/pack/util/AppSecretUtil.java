package com.doraemon.wish.pack.util;

public class AppSecretUtil {

    public static String encryptAppSecret(String appSecret) {
        String key = generateKey(32);

        return null;
    }

    static String generateKey(int length) {
        char seed = 100;
        int value = 3;
        char[] result = new char[length];
        for(int i=0; i<length; i++) {
            char temp = (char) (seed / (i+1));
            while (temp < 33 || temp > 126) {
                if(temp < 33) {
                    temp *= value;
                } else if(temp > 126) {
                    temp /= value;
                }
            }
            result[i] = temp;
            seed = temp;
        }

        return new String(result);
    }
}
