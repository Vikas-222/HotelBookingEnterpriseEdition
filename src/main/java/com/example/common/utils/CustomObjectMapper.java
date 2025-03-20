package com.example.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

public class CustomObjectMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T toObject(BufferedReader reader, Class<T> targetClass) throws IOException {
        return objectMapper.readValue(reader, targetClass);
    }

    public static <T> String toString(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
