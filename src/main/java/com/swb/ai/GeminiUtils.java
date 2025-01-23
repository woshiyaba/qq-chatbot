package com.swb.ai;

import com.swb.util.FileUtils;
import com.swb.util.HttpClientUtils;
import com.swb.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/22 
 **/
public class GeminiUtils {


    private static String API_KEY = "AIzaSyBlYTZbtmTWAIiP_tuk3VbfcHoZCClDOBA";
    private static final String URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;
    private static final Logger LOGGER = LoggerFactory.getLogger(GeminiUtils.class);

    private static List<Content> CONTEXT = new ArrayList<>();

    public static class Content {
        public String role; // "user" 或 "model"
        public String text; // 消息内容

        public Content(String role, String text) {
            this.role = role;
            this.text = text;
        }
    }

    public static void init(String apiKeyPath) {
        API_KEY = FileUtils.readFile(apiKeyPath);
    }

    public static String sendMessage(String message) {
        // 构建JSON请求体
        CONTEXT.add(new Content("user", message));
        String response = HttpClientUtils.sendJsonPostProxy(URL, buildRequestBody(CONTEXT));
        String result = parseResponse(response);
        CONTEXT.add(new Content("model", result));
        return result;
    }

    public static String parseResponse(String response) {
        try {
            return JsonUtils.toJsonNode(response)
                    .get("candidates").get(0)
                    .get("content")
                    .get("parts").get(0)
                    .get("text").asText();
        } catch (Exception e) {
            LOGGER.error("解析失败", e);
        }
        return null;
    }

    private static String buildRequestBody(List<GeminiUtils.Content> contents) {
        StringBuilder json = new StringBuilder("{\"contents\": [");

        // 将对话历史添加到请求体
        for (GeminiUtils.Content content : contents) {
            json.append("{")
                    .append("\"role\": \"").append(content.role).append("\",")
                    .append("\"parts\": [{\"text\": \"").append(content.text).append("\"}]")
                    .append("},");
        }

        // 删除最后一个逗号
        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("]}");
        return json.toString();
    }
}
