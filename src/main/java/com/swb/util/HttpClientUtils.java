package com.swb.util;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class HttpClientUtils {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    public static String sendJsonPost(String url, String key, String json) {
        RequestBody body = RequestBody.create(json,
                MediaType.parse("application/json"));

        Request.Builder builder = new Request.Builder()
                .url(url);
        if (key != null) {
            builder.addHeader("Authorization", key);
        }
        Request request = builder.post(body)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println(response.body().string());
                LOGGER.error("OKHTTP请求异常， {}", response.body().string());
                return null;
            }
            assert response.body() != null;
            return response.body().string();
        } catch (Exception e) {
            LOGGER.error("OKHTTP请求异常，", e);
        }
        return null;
    }

    public static String sendJsonPost(String url, String json) throws IOException {
        return sendJsonPost(url, null, json);
    }
}
