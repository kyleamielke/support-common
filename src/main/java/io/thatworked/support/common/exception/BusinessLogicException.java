package io.thatworked.support.common.exception;

import io.thatworked.support.common.logging.error.ErrorCode;

/**
 * Exception thrown when business rules are violated.
 * Use this for domain-specific validation failures.
 */
public class BusinessLogicException extends BaseException {
    
    public BusinessLogicException(String message) {
        super(ErrorCode.BIZ_001, message);
    }
    
    public BusinessLogicException(String message, Throwable cause) {
        super(ErrorCode.BIZ_001, message, cause);
    }
    
    public BusinessLogicException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}