package io.thatworked.support.common.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Jackson module for common serialization/deserialization configurations.
 * Ensures consistent handling of dates, times, and other common types across all services.
 */
public class CommonJacksonModule extends SimpleModule {
    
    public CommonJacksonModule() {
        super("CommonJacksonModule");
        
        // Register custom serializers/deserializers
        addSerializer(Instant.class, InstantSerializer.INSTANCE);
        addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        
        // Add more custom serializers/deserializers as needed
    }
}