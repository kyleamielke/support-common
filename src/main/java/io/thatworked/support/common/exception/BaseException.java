package io.thatworked.support.common.exception;

import io.thatworked.support.common.logging.error.ErrorCode;
import lombok.Getter;

/**
 * Base exception class for all custom exceptions in the system.
 * Provides consistent error handling with error codes.
 */
@Getter
public abstract class BaseException extends RuntimeException {
    
    private final ErrorCode errorCode;
    private final String userMessage;
    
    protected BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.userMessage = message;
    }
    
    protected BaseException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.userMessage = message;
    }
    
    protected BaseException(ErrorCode errorCode, String message, String userMessage) {
        super(message);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
    }
    
    protected BaseException(ErrorCode errorCode, String message, String userMessage, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
    }
}