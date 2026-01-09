package com.learn.springbootcrud.common.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springbootcrud.common.BusinessException;

@Component
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {}

    public static <T> T getValue(String jsonStr, String field, Class<T> clazz) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonStr);
            JsonNode fieldNode = jsonNode.get(field);

            if (fieldNode == null) {
                throw new BusinessException("Json String is not exist : " + field);
            }
            return convertValue(fieldNode, clazz);
        } catch (JsonProcessingException e) {
            // JSON格式错误（如非合法JSON字符串）
            throw new BusinessException("JSON parser Error, JSON fromat error, reason: " + e.getMessage());
        } catch (Exception e) {
            // 其他异常（如类型转换失败）
            throw new BusinessException("JSON transform error, filed = " + field + ", target Type = " + clazz.getName() + ", reason: " + e.getMessage());
        }
    }

    private static <T> T convertValue(JsonNode node, Class<T> clazz) {
        if (clazz == String.class) {
            return clazz.cast(node.asText());
        } else if (clazz == Integer.class || clazz == int.class) {
            return clazz.cast(node.asInt());
        } else if (clazz == Long.class || clazz == long.class) {
            return clazz.cast(node.asLong());
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            return clazz.cast(node.asBoolean());
        } else if (clazz == Double.class || clazz == double.class) {
            return clazz.cast(node.asDouble());
        } else {
            throw new BusinessException("Cannot transform other Types: " + clazz.getName());
        }
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new BusinessException("对象转JSON失败：" + e.getMessage());
        }
    }

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            throw new BusinessException("对象转JSON失败：" + e.getMessage());
        }
    }
}
