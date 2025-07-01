package io.thatworked.support.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Standard API response wrapper for consistent responses across all services.
 * 
 * @param <T> The type of data being returned
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    /**
     * The actual response data.
     */
    private T data;
    
    /**
     * Response metadata.
     */
    @Builder.Default
    private ResponseMetadata meta = ResponseMetadata.builder().build();
    
    /**
     * Create a successful response.
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .meta(ResponseMetadata.success())
                .build();
    }
    
    /**
     * Create an error response.
     */
    public static <T> ApiResponse<T> error(String message, String code) {
        return ApiResponse.<T>builder()
                .data(null)
                .meta(ResponseMetadata.error(message, code))
                .build();
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseMetadata {
        
        @Builder.Default
        private Instant timestamp = Instant.now();
        
        @Builder.Default
        private boolean success = true;
        
        private String message;
        
        private String errorCode;
        
        private String version;
        
        private Map<String, Object> additionalInfo;
        
        public static ResponseMetadata success() {
            return ResponseMetadata.builder()
                    .success(true)
                    .build();
        }
        
        public static ResponseMetadata error(String message, String code) {
            return ResponseMetadata.builder()
                    .success(false)
                    .message(message)
                    .errorCode(code)
                    .build();
        }
    }
}