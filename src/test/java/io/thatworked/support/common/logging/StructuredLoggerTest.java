package io.thatworked.support.common.logging;

import io.thatworked.support.common.logging.correlation.CorrelationIdConstants;
import io.thatworked.support.common.logging.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StructuredLoggerTest {
    
    private StructuredLogger logger;
    
    @BeforeEach
    void setUp() {
        logger = StructuredLogger.getLogger(StructuredLoggerTest.class, "test-service");
    }
    
    @Test
    void testBasicLogging() {
        // Test basic info logging
        assertDoesNotThrow(() -> logger.info("Test message"));
        
        // Test info logging with context
        assertDoesNotThrow(() -> logger.info("Test message with context", 
            Map.of("key1", "value1", "key2", 123)));
    }
    
    @Test
    void testErrorLogging() {
        Exception testException = new RuntimeException("Test exception");
        
        // Test error logging with exception
        assertDoesNotThrow(() -> logger.error("Error message", testException));
        
        // Test error logging with context and exception
        assertDoesNotThrow(() -> logger.error("Error with context", 
            Map.of("errorCode", "TEST_001"), testException));
    }
    
    @Test
    void testFluentAPI() {
        // Test fluent API
        assertDoesNotThrow(() -> 
            logger.with("userId", "12345")
                  .with("action", "login")
                  .with("success", true)
                  .info("User logged in successfully")
        );
    }
    
    @Test
    void testPerformanceLogging() {
        // Test performance logging
        assertDoesNotThrow(() -> 
            logger.performance("database.query", 150L, 
                Map.of("query", "SELECT * FROM users", "rows", 10))
        );
    }
    
    @Test
    void testSecurityLogging() {
        // Test security logging
        assertDoesNotThrow(() -> 
            logger.security("LOGIN_ATTEMPT", "user123", "/api/login", "POST", true)
        );
    }
    
    @Test
    void testMeasureOperation() throws Exception {
        // Test measure operation
        String result = logger.measure("test.operation", () -> {
            Thread.sleep(100);
            return "success";
        });
        
        assertEquals("success", result);
    }
    
    @Test
    void testCorrelationId() {
        String correlationId = UUID.randomUUID().toString();
        
        try {
            MDC.put(CorrelationIdConstants.CORRELATION_ID_MDC_KEY, correlationId);
            
            // Log should include correlation ID from MDC
            assertDoesNotThrow(() -> logger.info("Test with correlation ID"));
            
        } finally {
            MDC.clear();
        }
    }
    
    @Test
    void testErrorCodes() {
        // Test error code usage
        ErrorCode errorCode = ErrorCode.DB_001;
        
        assertDoesNotThrow(() -> 
            logger.with("errorCode", errorCode.getCode())
                  .with("errorDescription", errorCode.getDescription())
                  .error("Database connection failed", new RuntimeException("Connection refused"))
        );
    }
}