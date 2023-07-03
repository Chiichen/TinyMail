package edu.scut.tinymail.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

public class FileUtil {

    private FileUtil() {
    }
    public static byte[] trans(String base64File){
        return Base64.getDecoder().decode(base64File);
    }
    public static void createFile(byte[] file,byte[] filepath,String filename) throws IOException {
        String filePath = new String(filepath);
        File dir = new File(filePath);
        // 检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }
        File checkFile = new File(filePath + "/filename.txt");
        FileWriter writer = null;
        try {
            // 检查目标文件是否存在，不存在则创建
            if (!checkFile.exists()) {
                checkFile.createNewFile();// 创建目标文件
            }
            // 向目标文件中写入内容
            writer = new FileWriter(checkFile, true);
            writer.append(new String(file));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer)
                writer.close();
        }
    }
}
