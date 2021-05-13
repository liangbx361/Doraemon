package com.droaemon.common.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件操作工具类
 */
public class FileUtil {
    /**
     * 读取文件内容为二进制数组
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] read(String filePath) throws IOException {

        InputStream in = new FileInputStream(filePath);
        byte[] data = inputStream2ByteArray(in);
        in.close();

        return data;
    }

    /**
     * 流转二进制数组
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static byte[] inputStream2ByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    /**
     * 保存文件
     */
    public static void save(String filePath, String fileName, byte[] content) throws IOException {
        File pathFile = new File(filePath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        File file = new File(pathFile, fileName);
        OutputStream os = new FileOutputStream(file);
        os.write(content, 0, content.length);
        os.flush();
        os.close();
    }

    public static void saveMultiFile(String basePath, MultipartFile file) {
        MultipartFile[] files = new MultipartFile[1];
        files[0] = file;
        saveMultiFiles(basePath, files);
    }

    /**
     * 在basePath下保存上传的文件夹
     *
     * @param basePath
     * @param files
     */
    public static void saveMultiFiles(String basePath, MultipartFile[] files) {
        if (files == null || files.length == 0) {
            return;
        }
        if (basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }
        for (MultipartFile file : files) {
            String filePath = basePath + "/" + file.getOriginalFilename();
            makeDir(filePath);
            File dest = new File(filePath);
            try {
                file.transferTo(dest);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 确保目录存在，不存在则创建
     *
     * @param filePath
     */
    private static void makeDir(String filePath) {
        if (filePath.lastIndexOf('/') > 0) {
            String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

    public static void copy(File srcDir, File destDir) throws IOException {
        if (srcDir.isFile()) {
            FileUtils.copyFile(srcDir, destDir);
            return;
        }

        File[] fileList = srcDir.listFiles();
        if (fileList == null) {
            return;
        }

        for (File file : fileList) {
            if (file.isFile()) {
                FileUtils.copyFileToDirectory(file, destDir);
            } else {
                FileUtils.copyDirectoryToDirectory(file, destDir);
            }
        }
    }

    public static void delteContent(File dir) throws IOException {
        FileUtils.deleteDirectory(dir);
    }
}
