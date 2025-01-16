package com.swb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // 忽略未知字段，防止反序列化时出错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 将对象序列化为JSON字符串
     *
     * @param object 待序列化对象
     * @return JSON字符串
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("序列化对象为JSON字符串失败", e);
        }
    }

    /**
     * 将JSON字符串反序列化为对象
     *
     * @param json  JSON字符串
     * @param clazz 目标对象类
     * @param <T>   泛型
     * @return 目标对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("反序列化JSON字符串失败", e);
        }
    }

    /**
     * 将JSON字符串反序列化为指定类型对象，例如List、Map等
     *
     * @param json          JSON字符串
     * @param typeReference 类型引用
     * @param <T>           泛型
     * @return 目标对象
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("反序列化JSON字符串为复杂对象失败", e);
        }
    }

    /**
     * 将JSON字符串反序列化为List
     *
     * @param json  JSON字符串
     * @param clazz List中元素类型
     * @param <T>   泛型
     * @return List对象
     */
    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException("反序列化JSON字符串为List失败", e);
        }
    }

    /**
     * 将JSON字符串反序列化为Map
     *
     * @param json       JSON字符串
     * @param keyClass   Map的key类型
     * @param valueClass Map的value类型
     * @param <K>        key泛型
     * @param <V>        value泛型
     * @return Map对象
     */
    public static <K, V> Map<K, V> fromJsonToMap(String json, Class<K> keyClass, Class<V> valueClass) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
        } catch (IOException e) {
            throw new RuntimeException("反序列化JSON字符串为Map失败", e);
        }
    }

    /**
     * 将JSON字符串转换为JsonNode对象
     *
     * @param json JSON字符串
     * @return JsonNode对象
     */
    public static JsonNode toJsonNode(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("将字符串转换为JsonNode对象失败", e);
        }
    }
}
