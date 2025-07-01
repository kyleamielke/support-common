# Common Module Implementation Plan

## Phase 1: Critical Items for Immediate Implementation

### 1. BaseDTO Consolidation

**Current Duplication**
- `/device-service/src/main/java/io/thatworked/support/device/api/dto/common/BaseDTO.java`
- `/site-service/src/main/java/io/thatworked/support/site/dto/BaseDTO.java`

**Action**: Move to `/common/src/main/java/io/thatworked/support/common/dto/BaseDTO.java`

```java
package io.thatworked.support.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDTO {
    private UUID uuid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String lastModifiedBy;
}
```

### 2. Common Exception Hierarchy

**Create Base Structure**
```java
// BaseServiceException.java
package io.thatworked.support.common.exception;

public abstract class BaseServiceException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, Object> context;
    
    protected BaseServiceException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.context = new HashMap<>();
    }
    
    protected BaseServiceException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.context = new HashMap<>();
    }
    
    public BaseServiceException withContext(String key, Object value) {
        this.context.put(key, value);
        return this;
    }
}

// ResourceNotFoundException.java
package io.thatworked.support.common.exception;

public class ResourceNotFoundException extends BaseServiceException {
    public ResourceNotFoundException(String resourceType, String identifier) {
        super(ErrorCode.RESOURCE_NOT_FOUND, 
              String.format("%s not found with identifier: %s", resourceType, identifier));
        withContext("resourceType", resourceType)
            .withContext("identifier", identifier);
    }
}

// DuplicateResourceException.java
package io.thatworked.support.common.exception;

public class DuplicateResourceException extends BaseServiceException {
    public DuplicateResourceException(String resourceType, String field, String value) {
        super(ErrorCode.DUPLICATE_RESOURCE,
              String.format("%s with %s '%s' already exists", resourceType, field, value));
        withContext("resourceType", resourceType)
            .withContext("field", field)
            .withContext("value", value);
    }
}
```

### 3. Common Constants

**Create CommonConstants.java**
```java
package io.thatworked.support.common.constants;

public final class CommonConstants {
    private CommonConstants() {}
    
    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final int MIN_PAGE_SIZE = 1;
    
    // Validation
    public static final int MAX_NAME_LENGTH = 255;
    public static final int MAX_DESCRIPTION_LENGTH = 1000;
    public static final int MIN_NAME_LENGTH = 1;
    
    // Kafka
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    public static final String EVENT_TYPE_HEADER = "X-Event-Type";
    public static final String SOURCE_SERVICE_HEADER = "X-Source-Service";
    public static final String TIMESTAMP_HEADER = "X-Timestamp";
    
    // Cache
    public static final String CACHE_KEY_SEPARATOR = ":";
    public static final String CACHE_KEY_PREFIX = "support";
    public static final long DEFAULT_CACHE_TTL = 3600; // 1 hour in seconds
    
    // HTTP Headers
    public static final String API_KEY_HEADER = "X-API-Key";
    public static final String REQUEST_ID_HEADER = "X-Request-ID";
    
    // Regex Patterns
    public static final String IP_PATTERN = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final String MAC_PATTERN = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
    public static final String HOSTNAME_PATTERN = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
}
```

### 4. Common Enums

**Create Standard Enums**
```java
// OperationType.java
package io.thatworked.support.common.enums;

public enum OperationType {
    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete"),
    BULK_CREATE("bulk_create"),
    BULK_UPDATE("bulk_update"),
    BULK_DELETE("bulk_delete"),
    SEARCH("search"),
    EXPORT("export"),
    IMPORT("import");
    
    private final String value;
    
    OperationType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}

// EntityStatus.java
package io.thatworked.support.common.enums;

public enum EntityStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    PENDING("pending"),
    SUSPENDED("suspended"),
    DELETED("deleted"),
    ARCHIVED("archived");
    
    private final String value;
    
    EntityStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static EntityStatus fromValue(String value) {
        for (EntityStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
```

### 5. Common Utilities

**StringUtils.java**
```java
package io.thatworked.support.common.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
    
    public static String sanitize(String input) {
        if (input == null) return null;
        return input.trim().replaceAll("[<>\"'&]", "");
    }
    
    public static String truncate(String input, int maxLength) {
        if (input == null || input.length() <= maxLength) return input;
        return input.substring(0, maxLength);
    }
    
    public static String toSnakeCase(String input) {
        if (input == null) return null;
        return input.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
    
    public static String toCamelCase(String input) {
        if (input == null) return null;
        String[] parts = input.split("_");
        StringBuilder result = new StringBuilder(parts[0].toLowerCase());
        for (int i = 1; i < parts.length; i++) {
            result.append(parts[i].substring(0, 1).toUpperCase())
                  .append(parts[i].substring(1).toLowerCase());
        }
        return result.toString();
    }
    
    public static boolean isBlank(String input) {
        return input == null || input.trim().isEmpty();
    }
    
    public static boolean isNotBlank(String input) {
        return !isBlank(input);
    }
    
    public static String defaultIfBlank(String input, String defaultValue) {
        return isBlank(input) ? defaultValue : input;
    }
}
```

**CollectionUtils.java**
```java
package io.thatworked.support.common.util;

import lombok.experimental.UtilityClass;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class CollectionUtils {
    
    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (list == null || list.isEmpty()) return Collections.emptyList();
        
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }
    
    public static <T> List<T> deduplicate(List<T> list) {
        if (list == null) return null;
        return new ArrayList<>(new LinkedHashSet<>(list));
    }
    
    public static <T> Optional<T> safeGet(List<T> list, int index) {
        if (list == null || index < 0 || index >= list.size()) {
            return Optional.empty();
        }
        return Optional.ofNullable(list.get(index));
    }
    
    public static <T, R> List<R> transformList(List<T> list, Function<T, R> transformer) {
        if (list == null) return null;
        return list.stream()
                   .map(transformer)
                   .collect(Collectors.toList());
    }
    
    public static <T> boolean isNullOrEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
    
    public static <T> boolean isNotNullOrEmpty(Collection<T> collection) {
        return !isNullOrEmpty(collection);
    }
}
```

### 6. Common Validation Annotations

**Additional Validations Beyond IP/MAC**
```java
// ValidEmail.java
package io.thatworked.support.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message() default "Invalid email address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

// ValidHostname.java
package io.thatworked.support.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HostnameValidator.class)
@Documented
public @interface ValidHostname {
    String message() default "Invalid hostname";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

// ValidPort.java
package io.thatworked.support.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PortValidator.class)
@Documented
public @interface ValidPort {
    String message() default "Invalid port number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int min() default 1;
    int max() default 65535;
}
```

### 7. Common Logging Aspect

**LogExecutionAspect.java**
```java
package io.thatworked.support.common.aspect;

import io.thatworked.support.common.logging.StructuredLogger;
import io.thatworked.support.common.annotation.LogExecution;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class LogExecutionAspect {
    
    private static final StructuredLogger logger = StructuredLogger.getLogger(LogExecutionAspect.class, "common");
    
    @Around("@annotation(logExecution)")
    public Object logExecution(ProceedingJoinPoint joinPoint, LogExecution logExecution) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String operationId = UUID.randomUUID().toString();
        
        MDC.put("operationId", operationId);
        
        var contextLogger = logger
                .with("class", className)
                .with("method", methodName)
                .with("operationId", operationId);
        
        if (!logExecution.value().isEmpty()) {
            contextLogger = contextLogger.with("operation", logExecution.value());
        }
        
        if (logExecution.logArgs() && joinPoint.getArgs().length > 0) {
            contextLogger = contextLogger.with("args", Arrays.toString(joinPoint.getArgs()));
        }
        
        contextLogger.info("Method execution started");
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            var resultLogger = logger
                    .with("class", className)
                    .with("method", methodName)
                    .with("operationId", operationId);
            
            if (logExecution.logTime()) {
                resultLogger = resultLogger.with("executionTimeMs", executionTime);
            }
            
            if (logExecution.logResult() && result != null) {
                resultLogger = resultLogger.with("resultType", result.getClass().getSimpleName());
            }
            
            resultLogger.info("Method execution completed successfully");
            
            return result;
            
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            
            logger.with("class", className)
                    .with("method", methodName)
                    .with("operationId", operationId)
                    .with("executionTimeMs", executionTime)
                    .with("exceptionType", e.getClass().getSimpleName())
                    .with("exceptionMessage", e.getMessage())
                    .error("Method execution failed", e);
            
            throw e;
            
        } finally {
            MDC.remove("operationId");
        }
    }
}
```

## Migration Steps for Each Service

### 1. Device Service
- Replace local BaseDTO with common BaseDTO
- Remove duplicate validation annotations (IP, MAC)
- Replace DeviceServiceUtils generic methods with common utilities
- Update imports
- Remove local Constants class, use CommonConstants

### 2. API Gateway
- Replace local CursorUtil with enhanced common version
- Use common exception classes
- Replace ServiceLoggingAspect with common LogExecutionAspect
- Update GraphQL error handling to use common exceptions

### 3. Site Service
- Replace local BaseDTO with common BaseDTO
- Extract file validation logic to common FileUtils
- Use common validation annotations

### 4. Alert Service
- Use common enums for Priority and Severity
- Replace custom exceptions with common ones
- Use common event base classes

### 5. Ping Service
- Use common monitoring constants
- Replace custom validation with common validators
- Use common utility classes

### 6. Dashboard Cache Service
- Use common cache key patterns
- Replace custom DTOs with common ones
- Use common health status enums

### 7. Notification Service
- Use common event classes
- Add email validation using common validators
- Use common template utilities

### 8. Report Service
- Move FileStorageService patterns to common
- Use common file utilities
- Standardize report format enums

## Testing Strategy

1. **Unit Tests for All Utilities**
   - 100% coverage for utility classes
   - Edge case testing for validators
   - Performance tests for heavy utilities

2. **Integration Tests**
   - Test aspect behavior
   - Validate exception handling
   - Test serialization/deserialization

3. **Migration Tests**
   - Parallel testing (old vs new)
   - Regression test suite
   - Performance comparison

## Success Metrics

- Code duplication reduced by 40%
- Build time improved by 15%
- Test coverage increased to 90%
- Zero regression issues
- All services using common module

## Timeline

- Week 1: Implement Phase 1 items in common module
- Week 2: Migrate device-service and api-gateway
- Week 3: Migrate remaining services
- Week 4: Testing and documentation