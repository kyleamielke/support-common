# Common Module Migration Guide

This guide helps you migrate existing services to use the standardized components from the common module.

## Table of Contents
1. [Adding Common Module Dependency](#adding-common-module-dependency)
2. [Migrating Entities](#migrating-entities)
3. [Migrating DTOs](#migrating-dtos)
4. [Migrating Events](#migrating-events)
5. [Migrating Validation](#migrating-validation)
6. [Common Pitfalls](#common-pitfalls)

## Adding Common Module Dependency

Add to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":common"))
}
```

## Migrating Entities

### Before
```java
@Entity
public class Device {
    @Id
    private UUID uuid;  // Wrong field name
    private String deviceName;  // Wrong field name
    private LocalDateTime createdDate;  // Wrong type
    private LocalDateTime modifiedAt;  // Wrong name and type
}
```

### After
```java
@Entity
public class Device extends AuditableEntity {
    // id, createdAt, updatedAt, createdBy, updatedBy inherited
    private String name;  // Correct field name
    private String ipAddress;
    // other device-specific fields
}
```

### Migration Steps
1. Extend `BaseEntity` or `AuditableEntity`
2. Remove duplicate fields (id, timestamps, audit fields)
3. Rename fields to match standards:
   - `uuid` → remove (use inherited `id`)
   - `deviceName` → `name`
   - `createdDate` → remove (use inherited `createdAt`)
   - `modifiedAt` → remove (use inherited `updatedAt`)
4. Change timestamp types from `LocalDateTime` to `Instant`

## Migrating DTOs

### Before
```java
public class DeviceDTO {
    private UUID deviceId;
    private String deviceName;
    private LocalDateTime timestamp;
}

public class DevicePageResponse {
    private List<DeviceDTO> devices;
    private int page;
    private int totalPages;
}
```

### After
```java
public class DeviceResponse {
    private UUID id;  // Not deviceId
    private String name;  // Not deviceName
    private Instant createdAt;  // Not timestamp
}

// Use standard PageResponse
PageResponse<DeviceResponse> response = PageResponse.<DeviceResponse>builder()
    .content(devices)
    .pageNumber(0)
    .pageSize(20)
    .totalElements(100)
    .build();
```

## Migrating Events

### Before
```java
public class DeviceEvent {
    private String action;  // "device.created"
    private Date timestamp;
    private Map<String, Object> data;
}
```

### After
```java
@SuperBuilder
public class DeviceCreatedEvent extends BaseEvent {
    private final DevicePayload payload;
    
    @Override
    public String getAggregateType() {
        return "Device";
    }
    
    @Override
    public UUID getAggregateId() {
        return payload.getDeviceId();
    }
}

// Usage
DeviceCreatedEvent event = DeviceCreatedEvent.builder()
    .eventType("DEVICE_CREATED")  // UPPER_SNAKE_CASE
    .correlationId(correlationId)
    .metadata(EventMetadata.builder()
        .userId(currentUser)
        .source("device-service")
        .build())
    .payload(devicePayload)
    .build();
```

## Migrating Validation

### Before
```java
public class DeviceRequest {
    @Pattern(regexp = "complex-ip-regex")
    private String ipAddress;
    
    @Pattern(regexp = "complex-mac-regex")
    private String macAddress;
}
```

### After
```java
public class DeviceRequest {
    @ValidIpAddress
    private String ipAddress;
    
    @ValidMacAddress
    private String macAddress;
}
```

## Common Pitfalls

### 1. Forgetting to Update Database Migrations
When changing field names, you need to update your Flyway/Liquibase scripts:

```sql
-- Add migration to rename columns
ALTER TABLE device RENAME COLUMN uuid TO id;
ALTER TABLE device RENAME COLUMN device_name TO name;
ALTER TABLE device RENAME COLUMN created_date TO created_at;
```

### 2. Breaking API Contracts
If you have existing APIs, consider supporting both old and new field names temporarily:

```java
@JsonProperty("deviceName")  // Support old name in JSON
public String getName() {
    return name;
}
```

### 3. Timezone Issues with Instant
Instant is always UTC. If you need specific timezone display:

```java
// Convert for display
ZonedDateTime userTime = instant.atZone(userTimeZone);
```

### 4. Spring Data Integration
The common module provides `toPageable()` method, but it's optional:

```java
// If Spring Data is available
Pageable pageable = pageRequest.toPageable();

// If not, use the fields directly
int offset = pageRequest.getPage() * pageRequest.getSize();
```

### 5. Validation Message Customization
Override default messages in your `messages.properties`:

```properties
ValidIpAddress.message=Please enter a valid IP address
ValidMacAddress.message=Please enter a valid MAC address
```

## Testing Your Migration

Run the architecture tests to ensure compliance:

```java
@Test
void shouldFollowArchitectureRules() {
    JavaClasses classes = new ClassFileImporter()
        .importPackages("com.example.myservice");
    
    ArchitectureRules.checkAllRules(classes);
}
```

## Gradual Migration Strategy

1. **Phase 1**: Add common module dependency
2. **Phase 2**: Migrate internal models (entities, domain)
3. **Phase 3**: Add adapters for external APIs
4. **Phase 4**: Deprecate old DTOs/endpoints
5. **Phase 5**: Remove deprecated code

## Questions?

If you encounter issues during migration, check:
1. The common module test examples
2. The device-service reference implementation
3. Create an issue in the repository