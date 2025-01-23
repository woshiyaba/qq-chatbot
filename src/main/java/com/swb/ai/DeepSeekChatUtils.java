package com.swb.ai;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swb.util.FileUtils;
import com.swb.util.HttpClientUtils;
import com.swb.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16
 **/
public class DeepSeekChatUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeepSeekChatUtils.class);
    public static final String API_URL = "https://api.deepseek.com/chat/completions";
    public static String API_KEY;

    public static final ArrayList<Content> CONTEXT = new ArrayList<>();

    public static class Content {
        public String role;
        public String content;

        public Content() {
        }

        public Content(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    public static String SYSTEM_CONTENT;

    public static void init(String promptPath, String apiKeyPath) {
        SYSTEM_CONTENT = FileUtils.getFileType(promptPath);
        API_KEY = FileUtils.readFile(apiKeyPath);
        LOGGER.debug("Initializing DeepSeekChatUtils... {}", SYSTEM_CONTENT);

    }

    public static synchronized String sendMessage(String message) throws JsonProcessingException {
        if (CONTEXT.size() > 50) {
            CONTEXT.subList(0, 25).clear();
        }
        LOGGER.info("Sending message: {}", message);
        CONTEXT.add(new Content("user", message));
        String response = HttpClientUtils.sendJsonPost(API_URL, API_KEY, createMessages(JSONUtil.toJsonStr(CONTEXT)));
        return parseResponse(response);
    }

    public static String parseResponse(String response) {
        try {
            JsonNode msgNode = JsonUtils.toJsonNode(response)
                    .get("choices")
                    .get(0).get("message");
            Content content = JsonUtils.fromJson(msgNode.toString(), Content.class);
            CONTEXT.add(content);
            JsonNode des = JsonUtils.toJsonNode(content.content);
            return des.get("des").asText();
        } catch (Exception e) {
            LOGGER.error("Error parsing response", e);
        }
        return null;
    }


    private static String createMessages(String message) throws JsonProcessingException {

        // 创建 ObjectMapper 实例
        ObjectMapper objectMapper = new ObjectMapper();

        // 创建请求体 Map
        Map<String, Object> requestBody = new HashMap<>();
        createFields(requestBody);
        // 创建消息列表
        List<Map<String, String>> messages = new ArrayList<>();
        // 系统消息
        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", objectMapper.writeValueAsString(SYSTEM_CONTENT));
        messages.add(systemMessage);
        // 用户消息
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", message);
        messages.add(userMessage);
        Map<String, String> responseFormat = new HashMap<>();
        responseFormat.put("type", "json_object");
        requestBody.put("response_format", responseFormat);
        // 将消息列表添加到请求体中
        requestBody.put("messages", messages);
        // 将请求体转换为 JSON 字符串
        return objectMapper.writeValueAsString(requestBody);
    }

    private static Map<String, Object> createFields(Map<String, Object> requestBody) {
        // 填充常规字段
        requestBody.put("model", "deepseek-chat");
        requestBody.put("frequency_penalty", 0);
        requestBody.put("max_tokens", 2048);
        requestBody.put("presence_penalty", 0);
        requestBody.put("temperature", 0.1);
        requestBody.put("top_p", 1);
        requestBody.put("stop", null);
        requestBody.put("stream", false);
        requestBody.put("stream_options", null);
        requestBody.put("tools", null);
        requestBody.put("tool_choice", "none");
        requestBody.put("logprobs", false);
        requestBody.put("top_logprobs", null);
        return requestBody;
    }
}
