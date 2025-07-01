package io.thatworked.support.common.health.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

/**
 * Health check result.
 */
@Data
@Builder
public class HealthCheck {
    
    /**
     * Component name.
     */
    private String name;
    
    /**
     * Health status.
     */
    private io.thatworked.support.common.health.status.HealthStatus status;
    
    /**
     * Health check message.
     */
    private String message;
    
    /**
     * Timestamp of the health check.
     */
    @Builder.Default
    private Instant timestamp = Instant.now();
    
    /**
     * Response time in milliseconds.
     */
    private Long responseTime;
    
    /**
     * Additional details.
     */
    private Map<String, Object> details;
    
    /**
     * Create a healthy status.
     */
    public static HealthCheck up(String name) {
        return HealthCheck.builder()
                .name(name)
                .status(io.thatworked.support.common.health.status.HealthStatus.UP)
                .message("Healthy")
                .build();
    }
    
    /**
     * Create an unhealthy status.
     */
    public static HealthCheck down(String name, String message) {
        return HealthCheck.builder()
                .name(name)
                .status(io.thatworked.support.common.health.status.HealthStatus.DOWN)
                .message(message)
                .build();
    }
    
    /**
     * Create a degraded status.
     */
    public static HealthCheck degraded(String name, String message) {
        return HealthCheck.builder()
                .name(name)
                .status(io.thatworked.support.common.health.status.HealthStatus.DEGRADED)
                .message(message)
                .build();
    }
}