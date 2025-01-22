package com.swb.util;

import com.sun.org.apache.bcel.internal.generic.PUSH;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Proxy;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class HttpClientUtils {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final OkHttpClient CLIENT_PROXY;
    private static final String PROXY_HOST = "localhost";
    private static final int PROXY_PORT = 10809; // 替换为你的代理端口

    private static final Proxy PROXY;
    private static final String PROXY_USER = "";
    private static final String PROXY_PWD = "";

    static {
        // 创建代理对象
        PROXY =
                new Proxy(Proxy.Type.HTTP, new java.net.InetSocketAddress(PROXY_HOST, PROXY_PORT));
        // 创建认证器
        Authenticator proxyAuthenticator = (route, response) -> {
            String credential = Credentials.basic(PROXY_USER, PROXY_PWD);
            return response.request().newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build();
        };

        // 创建OkHttp客户端并设置代理和认证器
        CLIENT_PROXY = new OkHttpClient.Builder()
                .proxy(PROXY)
                .proxyAuthenticator(proxyAuthenticator)
                .build();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtils.class);

    public static String sendJsonPost(String url, String key, String json) {
        return sendJsonPost(url, key, json, false);
    }

    public static String sendJsonPost(String url, String json) throws IOException {
        return sendJsonPost(url, null, json);
    }

    public static String sendJsonPostProxy(String url, String key, String json) {
        return sendJsonPost(url, key, json, true);
    }

    public static String sendJsonPostProxy(String url, String json) {
        return sendJsonPost(url, null, json, true);
    }


    public static String sendJsonPost(String url, String key, String json, boolean isProxy) {
        RequestBody body = RequestBody.create(json,
                MediaType.parse("application/json"));

        Request.Builder builder = new Request.Builder()
                .url(url);
        if (key != null) {
            builder.addHeader("Authorization", key);
        }
        OkHttpClient realClient = CLIENT;
        if (isProxy) {
            realClient = CLIENT_PROXY;
        }
        Request request = builder.post(body)
                .build();

        try (Response response = realClient.newCall(request).execute()) {
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
}
