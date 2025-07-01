package io.thatworked.support.common.dto.base;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Base DTO class providing common fields for all DTOs.
 * This is for DTOs that need audit information.
 * 
 * Note: This is different from BaseEntity which is for domain models.
 * DTOs might use LocalDateTime for API compatibility while entities use Instant.
 */
@Getter
@Setter
public abstract class BaseDTO {
    
    private UUID id;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    
    // Some services use LocalDateTime for API compatibility
    // Subclasses can override these if needed
    public java.time.LocalDateTime getCreatedDate() {
        return createdAt != null ? 
            java.time.LocalDateTime.ofInstant(createdAt, java.time.ZoneOffset.UTC) : null;
    }
    
    public java.time.LocalDateTime getUpdatedDate() {
        return updatedAt != null ? 
            java.time.LocalDateTime.ofInstant(updatedAt, java.time.ZoneOffset.UTC) : null;
    }
}