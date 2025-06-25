package io.thatworked.support.common.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import io.thatworked.support.common.logging.correlation.CorrelationIdConstants;
import io.thatworked.support.common.logging.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StructuredLoggerIntegrationTest {
    
    private StructuredLogger structuredLogger;
    private ListAppender<ILoggingEvent> listAppender;
    private Logger logger;
    
    @BeforeEach
    void setUp() {
        structuredLogger = StructuredLogger.getLogger(StructuredLoggerIntegrationTest.class, "test-service");
        
        // Get the underlying logger and add an appender to capture logs
        logger = (Logger) LoggerFactory.getLogger(StructuredLoggerIntegrationTest.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        logger.setLevel(Level.DEBUG);
    }
    
    @Test
    void testLogOutput() {
        // Test simple info log
        structuredLogger.info("Test message");
        
        assertEquals(1, listAppender.list.size());
        ILoggingEvent event = listAppender.list.get(0);
        assertEquals(Level.INFO, event.getLevel());
        assertTrue(event.getFormattedMessage().contains("Test message"));
        assertTrue(event.getFormattedMessage().contains("test-service"));
    }
    
    @Test
    void testLogWithCorrelationId() {
        String correlationId = UUID.randomUUID().toString();
        MDC.put(CorrelationIdConstants.CORRELATION_ID_MDC_KEY, correlationId);
        
        try {
            structuredLogger.info("Test with correlation ID");
            
            assertEquals(1, listAppender.list.size());
            ILoggingEvent event = listAppender.list.get(0);
            assertTrue(event.getFormattedMessage().contains(correlationId));
        } finally {
            MDC.clear();
        }
    }
    
    @Test
    void testLogWithContext() {
        structuredLogger.with("userId", "12345")
                       .with("action", "test")
                       .info("Action performed");
        
        assertEquals(1, listAppender.list.size());
        ILoggingEvent event = listAppender.list.get(0);
        String message = event.getFormattedMessage();
        assertTrue(message.contains("userId"));
        assertTrue(message.contains("12345"));
        assertTrue(message.contains("action"));
        assertTrue(message.contains("test"));
    }
    
    @Test
    void testErrorLogging() {
        Exception testException = new RuntimeException("Test error");
        
        structuredLogger.with("errorCode", ErrorCode.SYS_003.getCode())
                       .error("System error occurred", testException);
        
        assertEquals(1, listAppender.list.size());
        ILoggingEvent event = listAppender.list.get(0);
        assertEquals(Level.ERROR, event.getLevel());
        String message = event.getFormattedMessage();
        assertTrue(message.contains("SYS_003"));
        assertTrue(message.contains("RuntimeException"));
        assertTrue(message.contains("Test error"));
    }
    
    @Test
    void testPerformanceLogging() {
        structuredLogger.performance("test.operation", 150L, 
            java.util.Map.of("status", "success", "records", 100));
        
        assertEquals(1, listAppender.list.size());
        ILoggingEvent event = listAppender.list.get(0);
        String message = event.getFormattedMessage();
        assertTrue(message.contains("150"));
        assertTrue(message.contains("test.operation"));
        assertTrue(message.contains("success"));
    }
    
    @Test
    void testSecurityLogging() {
        structuredLogger.security("LOGIN", "testuser", "/api/secure", "GET", false);
        
        assertEquals(1, listAppender.list.size());
        ILoggingEvent event = listAppender.list.get(0);
        String message = event.getFormattedMessage();
        assertTrue(message.contains("LOGIN"));
        assertTrue(message.contains("testuser"));
        assertTrue(message.contains("/api/secure"));
        assertTrue(message.contains("false"));
    }
}