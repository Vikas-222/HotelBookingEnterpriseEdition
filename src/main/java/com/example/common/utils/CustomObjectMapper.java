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

//        SimpleModule timeModule = new SimpleModule();
//        timeModule.addSerializer(Time.class, new JsonSerializer<>() {
//            @Override
//            public void serialize(Time value, JsonGenerator gen, SerializerProvider provider) throws IOException {
//                gen.writeString(DateTimeFormatter.ofPattern(TIME_FORMAT).format(value.toLocalTime()));
//            }
//        });
//        timeModule.addDeserializer(Time.class, new JsonDeserializer<Time>() {
//            @Override
//            public Time deserialize(com.fasterxml.jackson.core.JsonParser p, DeserializationContext ctxt) throws IOException {
//                return Time.valueOf(java.time.LocalTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(TIME_FORMAT)));
//            }
//        });
//
//        // Register the module
//        objectMapper.registerModule(timeModule);
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
