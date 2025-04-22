package com.example.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;


public class CustomObjectMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TIME_FORMAT = "HH:mm:ss";

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }


    public static <T> T toObject(BufferedReader reader, Class<T> targetClass) throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(reader, targetClass);
    }

    public static <T> T toObject(BufferedReader reader, TypeReference<T> valueTypeRef) throws IOException {
        return objectMapper.readValue(reader, valueTypeRef);
    }

    public static <T> String toString(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T toObject(InputStream inputStream, Class<T> targetClass) throws IOException {
        return objectMapper.readValue(inputStream,targetClass);
    }
}
