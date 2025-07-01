package io.thatworked.support.common.enums;

/**
 * Standard operation types for audit trails and events.
 */
public enum OperationType {
    
    /**
     * Create/insert operation.
     */
    CREATE,
    
    /**
     * Read/retrieve operation.
     */
    READ,
    
    /**
     * Update/modify operation.
     */
    UPDATE,
    
    /**
     * Delete/remove operation.
     */
    DELETE,
    
    /**
     * Search/query operation.
     */
    SEARCH,
    
    /**
     * Import/bulk create operation.
     */
    IMPORT,
    
    /**
     * Export/bulk read operation.
     */
    EXPORT,
    
    /**
     * Archive operation.
     */
    ARCHIVE,
    
    /**
     * Restore from archive operation.
     */
    RESTORE,
    
    /**
     * Assign/link operation.
     */
    ASSIGN,
    
    /**
     * Unassign/unlink operation.
     */
    UNASSIGN,
    
    /**
     * Enable/activate operation.
     */
    ENABLE,
    
    /**
     * Disable/deactivate operation.
     */
    DISABLE
}