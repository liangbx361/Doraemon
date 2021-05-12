package com.droaemon.common.util;

import org.junit.Test;

import java.io.IOException;

public class ShellUtilTest {

    @Test
    public void testBuild() {
        try {
            ShellUtil.exec("g17173-pack -m -apk /Volumes/extend/doraemon/apk/tmns_1.0.4_cb_20210402170811.apk -c /Volumes/extend/doraemon/build -pluginDir /Volumes/extend/doraemon/plugin");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}