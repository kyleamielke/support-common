package io.thatworked.support.common.event.types;

/**
 * Common event types used across services.
 */
public final class EventType {
    
    // Device events
    public static final String DEVICE_CREATED = "device.created";
    public static final String DEVICE_UPDATED = "device.updated";
    public static final String DEVICE_DELETED = "device.deleted";
    public static final String DEVICE_STATUS_CHANGED = "device.status.changed";
    
    // Ping events
    public static final String PING_STARTED = "ping.started";
    public static final String PING_STOPPED = "ping.stopped";
    public static final String PING_SUCCESS = "ping.success";
    public static final String PING_FAILURE = "ping.failure";
    public static final String PING_TIMEOUT = "ping.timeout";
    
    // Alert events
    public static final String ALERT_CREATED = "alert.created";
    public static final String ALERT_UPDATED = "alert.updated";
    public static final String ALERT_RESOLVED = "alert.resolved";
    public static final String ALERT_ACKNOWLEDGED = "alert.acknowledged";
    public static final String ALERT_ESCALATED = "alert.escalated";
    
    // Notification events
    public static final String NOTIFICATION_SENT = "notification.sent";
    public static final String NOTIFICATION_FAILED = "notification.failed";
    public static final String NOTIFICATION_DELIVERED = "notification.delivered";
    public static final String NOTIFICATION_BOUNCED = "notification.bounced";
    
    // Report events
    public static final String REPORT_GENERATED = "report.generated";
    public static final String REPORT_FAILED = "report.failed";
    public static final String REPORT_SCHEDULED = "report.scheduled";
    
    // System events
    public static final String SERVICE_STARTED = "service.started";
    public static final String SERVICE_STOPPED = "service.stopped";
    public static final String SERVICE_HEALTH_CHECK = "service.health.check";
    public static final String SERVICE_ERROR = "service.error";
    
    private EventType() {
        // Utility class
    }
}