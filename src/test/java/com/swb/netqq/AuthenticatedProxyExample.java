package com.swb.netqq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swb.ai.DeepSeekChatUtils;
import com.swb.ai.GeminiUtils;
import com.swb.util.JsonUtils;
import okhttp3.*;
import org.junit.Test;

import java.net.Proxy;
import java.util.ArrayList;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/22 
 **/
public class AuthenticatedProxyExample {

    @Test
    public void test() {
        // 代理地址和端口
        String proxyHost = "localhost";
        int proxyPort = 10809; // 替换为你的代理端口

        // 代理认证信息
        String proxyUser = "";
        String proxyPassword = "";

        // 创建代理对象
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new java.net.InetSocketAddress(proxyHost, proxyPort));
        // 创建认证器
        Authenticator proxyAuthenticator = (route, response) -> {
            String credential = Credentials.basic(proxyUser, proxyPassword);
            return response.request().newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build();
        };

        // 创建OkHttp客户端并设置代理和认证器
        OkHttpClient client = new OkHttpClient.Builder()
                .proxy(proxy)
                .proxyAuthenticator(proxyAuthenticator)
                .build();

        // 目标URL
        String apiKey = "AIzaSyBlYTZbtmTWAIiP_tuk3VbfcHoZCClDOBA";
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;

        // 构建JSON请求体
        String json = "{"
                + "\"contents\": [{"
                + "\"parts\":[{\"text\": \"你是谁，请讲中文\"}]"
                + "}]"
                + "}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));

        // 创建请求
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json") // 设置请求头
                .build();

        // 发送请求并获取响应
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Response: " + response.body().string());
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGemini() {
        String s1 = GeminiUtils.sendMessage("你是谁，请讲中文");
        String s2 = GeminiUtils.sendMessage("你是谁，请讲中文");
        String s3 = GeminiUtils.sendMessage("你是谁，请讲中文");
        String s4 = GeminiUtils.sendMessage("我们一共进行了几次问答");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
    }

    @Test
    public void testContext() throws JsonProcessingException {
        ArrayList<DeepSeekChatUtils.Content> maps = new ArrayList<>();

        maps.add(new DeepSeekChatUtils.Content("user", "开始"));
        DeepSeekChatUtils.init("prompt_dm.txt");
        String s = DeepSeekChatUtils.sendMessage(JsonUtils.toJson(maps));
//        Map<String, String> stringStringMap = JsonUtils.fromJsonToMap("{\"role\":\"assistant\",\"content\":\"{\\\"des\\\":\\\"你决定探索森林。你离开小路，踏入茂密的森林。阳光透过树叶的缝隙，斑驳地照在地面上。你小心翼翼地前进，突然，你听到前方有轻微的沙沙声。你悄悄地靠近，发现是一只小鹿在吃草。就在这时，你注意到不远处有一群哥布林正在设置陷阱。他们似乎没有注意到你的存在。现在，你需要决定你的下一步行动。1. {悄悄绕过哥布林} 2. {尝试与哥布林交涉} 3. {准备战斗}\\\"}\"}", String.class, String.class);
        System.out.println(s);

    }

    @Test
    public void testContent() throws JsonProcessingException {
        System.out.println(DeepSeekChatUtils.SYSTEM_CONTENT);

    }

}
