package com.doraemon.wish.pack.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class AppSecretUtilTest {

    @Test
    void encryptAppSecret() {
        String encryptSecret = AppSecretUtil.generateKey(32);
        Assertions.assertEquals("d20$?Z$$$Q?-Q-Q-6Q$QQQQQQQQ6Q6Q6", encryptSecret);
    }
}