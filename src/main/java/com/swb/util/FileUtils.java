package com.swb.util;

import com.swb.ai.DeepSeekChatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/22 
 **/
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static String getFileType(String fileName) {
        try (InputStream inputStream = DeepSeekChatUtils.class.getClassLoader().getResourceAsStream(fileName);) {
            if (inputStream == null) {
                return "";
            }
            // 使用 ByteArrayOutputStream 来读取所有字节
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;

            // 将输入流中的内容写入 ByteArrayOutputStream
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }

            return byteArrayOutputStream.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            LOGGER.error("getFileType error", e);
            return "";
        }

    }

    public static String readFile(String filePath) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("readFile error", e);
        }
        return "";
    }
}
