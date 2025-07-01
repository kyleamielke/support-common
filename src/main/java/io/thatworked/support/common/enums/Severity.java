package io.thatworked.support.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Standard severity levels for alerts, logs, and incidents.
 */
@Getter
@RequiredArgsConstructor
public enum Severity {
    
    /**
     * Fatal severity - system is unusable.
     */
    FATAL(1, "Fatal", "System is unusable"),
    
    /**
     * Error severity - error conditions exist.
     */
    ERROR(2, "Error", "Error conditions exist"),
    
    /**
     * Warning severity - warning conditions exist.
     */
    WARNING(3, "Warning", "Warning conditions exist"),
    
    /**
     * Info severity - informational messages.
     */
    INFO(4, "Info", "Informational messages"),
    
    /**
     * Debug severity - debug-level messages.
     */
    DEBUG(5, "Debug", "Debug-level messages");
    
    private final int level;
    private final String displayName;
    private final String description;
    
    /**
     * Check if this severity is more severe than another.
     */
    public boolean isMoreSevereThan(Severity other) {
        return this.level < other.level;
    }
    
    /**
     * Check if this severity is less severe than another.
     */
    public boolean isLessSevereThan(Severity other) {
        return this.level > other.level;
    }
    
    /**
     * Convert from Priority enum.
     */
    public static Severity fromPriority(Priority priority) {
        return switch (priority) {
            case CRITICAL -> FATAL;
            case HIGH -> ERROR;
            case MEDIUM -> WARNING;
            case LOW, INFO -> INFO;
        };
    }
}