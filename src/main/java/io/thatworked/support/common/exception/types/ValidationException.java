package io.thatworked.support.common.exception.types;

import io.thatworked.support.common.exception.BaseException;
import io.thatworked.support.common.logging.error.ErrorCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception thrown when validation fails.
 * Can contain field-level validation errors.
 */
@Getter
public class ValidationException extends BaseException {
    
    private final Map<String, String> fieldErrors;
    
    public ValidationException(String message) {
        super(ErrorCode.VAL_001, message);
        this.fieldErrors = new HashMap<>();
    }
    
    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(ErrorCode.VAL_001, message);
        this.fieldErrors = fieldErrors != null ? new HashMap<>(fieldErrors) : new HashMap<>();
    }
    
    public ValidationException(String field, String error) {
        super(ErrorCode.VAL_001, String.format("Validation failed for field '%s': %s", field, error));
        this.fieldErrors = new HashMap<>();
        this.fieldErrors.put(field, error);
    }
    
    public void addFieldError(String field, String error) {
        fieldErrors.put(field, error);
    }
}