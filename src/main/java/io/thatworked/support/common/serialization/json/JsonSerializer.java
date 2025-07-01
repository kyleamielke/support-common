package io.thatworked.support.common.serialization.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Utility class for JSON serialization and deserialization.
 * Provides a pre-configured ObjectMapper with common settings.
 */
@Slf4j
public class JsonSerializer {
    
    private static final ObjectMapper OBJECT_MAPPER;
    
    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    private JsonSerializer() {
        // Utility class
    }
    
    /**
     * Serialize an object to JSON string.
     */
    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object to JSON", e);
            throw new io.thatworked.support.common.serialization.exception.SerializationException("Failed to serialize object", e);
        }
    }
    
    /**
     * Serialize an object to JSON bytes.
     */
    public static byte[] toJsonBytes(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object to JSON bytes", e);
            throw new io.thatworked.support.common.serialization.exception.SerializationException("Failed to serialize object", e);
        }
    }
    
    /**
     * Deserialize JSON string to object.
     */
    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.error("Failed to deserialize JSON to object", e);
            throw new io.thatworked.support.common.serialization.exception.SerializationException("Failed to deserialize JSON", e);
        }
    }
    
    /**
     * Deserialize JSON bytes to object.
     */
    public static <T> T fromJsonBytes(byte[] json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.error("Failed to deserialize JSON bytes to object", e);
            throw new io.thatworked.support.common.serialization.exception.SerializationException("Failed to deserialize JSON", e);
        }
    }
    
    /**
     * Deserialize JSON string to object with TypeReference.
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("Failed to deserialize JSON to object with TypeReference", e);
            throw new io.thatworked.support.common.serialization.exception.SerializationException("Failed to deserialize JSON", e);
        }
    }
    
    /**
     * Convert object to another type via JSON.
     */
    public static <T> T convert(Object object, Class<T> targetType) {
        return OBJECT_MAPPER.convertValue(object, targetType);
    }
    
    /**
     * Get the shared ObjectMapper instance.
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}