package io.thatworked.support.common.event.base;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Base class for all domain events.
 */
@Getter
@Setter
public abstract class BaseEvent {
    
    /**
     * Unique event ID.
     */
    private String eventId = UUID.randomUUID().toString();
    
    /**
     * Event type/name.
     */
    private String eventType;
    
    /**
     * When the event occurred.
     */
    private Instant timestamp = Instant.now();
    
    /**
     * Source service/system that generated the event.
     */
    private String source;
    
    /**
     * Event version for schema evolution.
     */
    private String version = "1.0";
    
    /**
     * Correlation ID for tracing related events.
     */
    private String correlationId;
    
    /**
     * User who triggered the event (if applicable).
     */
    private String userId;
    
    protected BaseEvent(String eventType, String source) {
        this.eventType = eventType;
        this.source = source;
    }
}