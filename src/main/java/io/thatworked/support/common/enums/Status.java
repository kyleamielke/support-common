package io.thatworked.support.common.enums;

/**
 * Standard status enum for entities.
 * Use this for general entity status across services.
 */
public enum Status {
    /**
     * Entity is active and operational.
     */
    ACTIVE,
    
    /**
     * Entity is temporarily disabled or offline.
     */
    INACTIVE,
    
    /**
     * Entity is awaiting some action or approval.
     */
    PENDING,
    
    /**
     * Entity is archived and no longer in active use.
     */
    ARCHIVED,
    
    /**
     * Entity is undergoing maintenance.
     */
    MAINTENANCE,
    
    /**
     * Entity has been decommissioned.
     */
    DECOMMISSIONED
}