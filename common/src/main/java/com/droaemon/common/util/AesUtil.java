package com.droaemon.common.util;

import org.springframework.util.Base64Utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AesUtil {

    private static final String KEY_ALGORITHM = "AES";
    //加密算法
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NoPadding";

    public static String encrypt(String content, byte[] key, byte[] iv) {
        try {
            byte[] contentByte = content.getBytes(StandardCharsets.UTF_8);

            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            IvParameterSpec parameters = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameters);
            byte[] result = cipher.doFinal(contentByte);

            //通过Base64转码返回
            return new String(Base64Utils.encode(result));
        } catch (InvalidKeyException
            | NoSuchAlgorithmException
            | NoSuchPaddingException
            | IllegalBlockSizeException
            | BadPaddingException
            | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static SecretKeySpec getSecretKey(final String key) {
        // 返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg;

        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            //AES 要求密钥长度为 128
            kg.init(128, new SecureRandom(key.getBytes()));
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            // 转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
