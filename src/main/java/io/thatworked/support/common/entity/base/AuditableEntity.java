package io.thatworked.support.common.entity.base;

import lombok.Getter;
import lombok.Setter;

/**
 * Extended base entity that includes user tracking fields.
 * Use this for entities that need to track who created/modified them.
 */
@Getter
@Setter
public abstract class AuditableEntity extends BaseEntity {
    
    /**
     * Username or ID of the user who created this entity.
     * Should be populated from security context or request headers.
     */
    private String createdBy;
    
    /**
     * Username or ID of the user who last updated this entity.
     * Should be updated on every modification.
     */
    private String updatedBy;
    
    /**
     * Override onCreate to also set createdBy.
     */
    @Override
    protected void onCreate() {
        super.onCreate();
        if (this.updatedBy == null && this.createdBy != null) {
            this.updatedBy = this.createdBy;
        }
    }
    
    /**
     * Override onUpdate to ensure updatedBy is set.
     */
    @Override
    protected void onUpdate() {
        super.onUpdate();
        // updatedBy should be set by the service layer from security context
    }
}