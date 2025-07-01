package io.thatworked.support.common.health.status;

/**
 * Health status enumeration.
 */
public enum HealthStatus {
    /**
     * Service is healthy and operational.
     */
    UP,
    
    /**
     * Service is unhealthy or experiencing issues.
     */
    DOWN,
    
    /**
     * Service is partially operational with degraded performance.
     */
    DEGRADED,
    
    /**
     * Health status is unknown.
     */
    UNKNOWN
}