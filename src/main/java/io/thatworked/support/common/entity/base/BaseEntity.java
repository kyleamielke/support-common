package io.thatworked.support.common.entity.base;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Base entity class that all domain entities should extend.
 * Provides common fields for all entities in the system.
 */
@Getter
@Setter
public abstract class BaseEntity {
    
    /**
     * Unique identifier for the entity.
     * Always use 'id' as the field name, not 'uuid' or '{entity}Id'.
     */
    private UUID id;
    
    /**
     * Timestamp when the entity was created.
     * Uses Instant for consistent timezone handling (UTC).
     */
    private Instant createdAt;
    
    /**
     * Timestamp when the entity was last updated.
     * Uses Instant for consistent timezone handling (UTC).
     */
    private Instant updatedAt;
    
    /**
     * Version number for optimistic locking.
     * Prevents concurrent modification issues.
     */
    private Long version;
    
    /**
     * Pre-persist callback to set creation timestamp and ID.
     */
    protected void onCreate() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
        this.updatedAt = this.createdAt;
    }
    
    /**
     * Pre-update callback to update modification timestamp.
     */
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}