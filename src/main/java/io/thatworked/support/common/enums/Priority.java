package io.thatworked.support.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Standard priority levels for use across services.
 * Can be used for alerts, tasks, tickets, etc.
 */
@Getter
@RequiredArgsConstructor
public enum Priority {
    
    /**
     * Critical priority - requires immediate attention.
     */
    CRITICAL(1, "Critical"),
    
    /**
     * High priority - should be addressed soon.
     */
    HIGH(2, "High"),
    
    /**
     * Medium priority - normal priority level.
     */
    MEDIUM(3, "Medium"),
    
    /**
     * Low priority - can be addressed when time permits.
     */
    LOW(4, "Low"),
    
    /**
     * Informational - for awareness only.
     */
    INFO(5, "Informational");
    
    private final int level;
    private final String displayName;
    
    /**
     * Check if this priority is higher than another.
     */
    public boolean isHigherThan(Priority other) {
        return this.level < other.level;
    }
    
    /**
     * Check if this priority is lower than another.
     */
    public boolean isLowerThan(Priority other) {
        return this.level > other.level;
    }
}