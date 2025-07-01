package io.thatworked.support.common.exception.types;

import io.thatworked.support.common.logging.error.ErrorCode;

import java.util.UUID;

/**
 * Exception thrown when a requested resource cannot be found.
 * Use this for 404 Not Found scenarios.
 */
import io.thatworked.support.common.exception.BaseException;

public class ResourceNotFoundException extends BaseException {
    
    public ResourceNotFoundException(String resourceType, UUID id) {
        super(
            ErrorCode.BIZ_003,
            String.format("%s not found with id: %s", resourceType, id),
            String.format("%s not found", resourceType)
        );
    }
    
    public ResourceNotFoundException(String resourceType, String identifier) {
        super(
            ErrorCode.BIZ_003,
            String.format("%s not found with identifier: %s", resourceType, identifier),
            String.format("%s not found", resourceType)
        );
    }
    
    public ResourceNotFoundException(String message) {
        super(ErrorCode.BIZ_003, message);
    }
}