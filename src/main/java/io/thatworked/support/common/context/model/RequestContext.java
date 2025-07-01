package io.thatworked.support.common.context.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Request context for distributed tracing and correlation.
 */
@Getter
@Builder
public class RequestContext {
    
    /**
     * Request ID for tracking.
     */
    @Builder.Default
    private final String requestId = UUID.randomUUID().toString();
    
    /**
     * Correlation ID for grouping related requests.
     */
    private final String correlationId;
    
    /**
     * User ID if authenticated.
     */
    private final String userId;
    
    /**
     * User roles/permissions.
     */
    private final String[] roles;
    
    /**
     * Request timestamp.
     */
    @Builder.Default
    private final Instant timestamp = Instant.now();
    
    /**
     * Source IP address.
     */
    private final String sourceIp;
    
    /**
     * User agent string.
     */
    private final String userAgent;
    
    /**
     * Additional headers.
     */
    private final Map<String, String> headers;
    
    /**
     * Request metadata.
     */
    private final Map<String, Object> metadata;
    
    /**
     * Get correlation ID or request ID if not set.
     */
    public String getEffectiveCorrelationId() {
        return correlationId != null ? correlationId : requestId;
    }
}