package io.thatworked.support.common.discovery.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Information about a service instance.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceInfo {
    
    /**
     * Service name.
     */
    private String name;
    
    /**
     * Service ID (unique instance identifier).
     */
    private String id;
    
    /**
     * Service host/IP address.
     */
    private String host;
    
    /**
     * Service port.
     */
    private int port;
    
    /**
     * Whether the service is healthy.
     */
    private boolean healthy;
    
    /**
     * Service version.
     */
    private String version;
    
    /**
     * Service metadata/tags.
     */
    private Map<String, String> metadata;
    
    /**
     * When the service was registered.
     */
    private Instant registeredAt;
    
    /**
     * Last health check timestamp.
     */
    private Instant lastHealthCheck;
    
    /**
     * Get the service URL.
     */
    public String getUrl() {
        return String.format("http://%s:%d", host, port);
    }
    
    /**
     * Get the service address.
     */
    public String getAddress() {
        return String.format("%s:%d", host, port);
    }
}