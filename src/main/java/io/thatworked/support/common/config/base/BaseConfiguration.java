package io.thatworked.support.common.config.base;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Base configuration class providing common properties.
 */
@Getter
@Setter
public abstract class BaseConfiguration {
    
    /**
     * Application name.
     */
    @NotNull
    private String applicationName;
    
    /**
     * Environment (dev, test, prod).
     */
    @NotNull
    private String environment = "dev";
    
    /**
     * Whether debug mode is enabled.
     */
    private boolean debugEnabled = false;
    
    /**
     * Service port.
     */
    private Integer port;
    
    /**
     * Service health check endpoint.
     */
    private String healthCheckEndpoint = "/actuator/health";
    
    /**
     * Validate the configuration.
     * Subclasses should override to add custom validation.
     */
    public void validate() {
        if (applicationName == null || applicationName.trim().isEmpty()) {
            throw new IllegalStateException("Application name is required");
        }
    }
    
    /**
     * Check if running in production.
     */
    public boolean isProduction() {
        return "prod".equalsIgnoreCase(environment) || 
               "production".equalsIgnoreCase(environment);
    }
    
    /**
     * Check if running in development.
     */
    public boolean isDevelopment() {
        return "dev".equalsIgnoreCase(environment) || 
               "development".equalsIgnoreCase(environment);
    }
}