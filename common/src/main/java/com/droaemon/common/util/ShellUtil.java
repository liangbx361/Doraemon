package com.droaemon.common.util;

import org.apache.tomcat.util.http.fileupload.util.Streams;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShellUtil {

    public static boolean exec(String command) throws IOException, InterruptedException {
        System.out.println("执行命令：" + command);
        Process process = Runtime.getRuntime().exec(command);
        int status = process.waitFor();
        saveLogFile(process);
        return status == 0;
    }

    private static void saveLogFile(Process process) throws FileNotFoundException {
        FileOutputStream inputStream = new FileOutputStream("input.log");
        FileOutputStream errorStream = new FileOutputStream("error.log");
        try {
            Streams.copy(process.getInputStream(), inputStream, true);
            Streams.copy(process.getErrorStream(), errorStream, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void execAndWait(String command) throws IOException, InterruptedException {
        Runtime.getRuntime().exec(command).waitFor();
    }

    public static List<String> readProcessInfo(Process process) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        List<String> lines = new ArrayList<>(200);
        try {
            inputStreamReader = new InputStreamReader(process.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            // read the output from the command
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
//                System.out.println(line);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getFieldValue(String field) {
        return field.substring(field.indexOf("=") + 2, field.length() - 1);
    }

    public static String getLineValue(String line) {
        return line.substring(line.indexOf(":") + 2, line.length() - 1);
    }
}
