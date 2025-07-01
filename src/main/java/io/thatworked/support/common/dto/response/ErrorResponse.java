package io.thatworked.support.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * Standard error response DTO.
 * Use this for all error responses across services.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /**
     * Timestamp when the error occurred.
     */
    @Builder.Default
    private Instant timestamp = Instant.now();
    
    /**
     * HTTP status code.
     */
    private int status;
    
    /**
     * HTTP error phrase (e.g., "Not Found", "Bad Request").
     */
    private String error;
    
    /**
     * User-friendly error message.
     */
    private String message;
    
    /**
     * The request path that caused the error.
     */
    private String path;
    
    /**
     * Unique trace ID for debugging.
     * Can be used to correlate with logs.
     */
    @Builder.Default
    private UUID traceId = UUID.randomUUID();
    
    /**
     * Optional field-level validation errors.
     */
    private java.util.Map<String, String> validationErrors;
    
    /**
     * Create an error response for a specific HTTP status.
     */
    public static ErrorResponse of(int status, String error, String message, String path) {
        return ErrorResponse.builder()
                .status(status)
                .error(error)
                .message(message)
                .path(path)
                .build();
    }
}