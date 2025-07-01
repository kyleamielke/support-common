package io.thatworked.support.common.exception.types;

import io.thatworked.support.common.logging.error.ErrorCode;

/**
 * Exception thrown when attempting to create a resource that already exists.
 * Use this for unique constraint violations and duplicate key scenarios.
 */
import io.thatworked.support.common.exception.BaseException;

public class DuplicateResourceException extends BaseException {
    
    public DuplicateResourceException(String resourceType, String identifier) {
        super(
            ErrorCode.DB_004,  // Duplicate key violation
            String.format("%s already exists with identifier: %s", resourceType, identifier),
            String.format("%s already exists", resourceType)
        );
    }
    
    public DuplicateResourceException(String message) {
        super(ErrorCode.DB_004, message);
    }
}