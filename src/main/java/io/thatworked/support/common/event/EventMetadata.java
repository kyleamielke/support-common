package io.thatworked.support.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard metadata for all events.
 * Provides context about who triggered the event and from where.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventMetadata {
    
    /**
     * ID of the user who triggered the event.
     * Could be username, email, or user ID.
     */
    private String userId;
    
    /**
     * Name of the service that published the event.
     * For example: "device-service", "alert-service"
     */
    private String source;
    
    /**
     * Version of the event schema.
     * Allows for backward compatibility.
     */
    @Builder.Default
    private String version = "1.0";
    
    /**
     * Optional additional context.
     * Can include things like IP address, user agent, etc.
     */
    private java.util.Map<String, Object> additionalContext;
}