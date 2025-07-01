package io.thatworked.support.common.constants;

/**
 * Common constants used across all services.
 */
public final class CommonConstants {
    
    private CommonConstants() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    // Pagination defaults
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final int DEFAULT_PAGE_NUMBER = 0;
    
    // Kafka headers
    public static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    public static final String USER_ID_HEADER = "X-User-ID";
    public static final String SOURCE_SERVICE_HEADER = "X-Source-Service";
    public static final String EVENT_TYPE_HEADER = "X-Event-Type";
    public static final String EVENT_VERSION_HEADER = "X-Event-Version";
    
    // Cache key patterns
    public static final String CACHE_KEY_SEPARATOR = ":";
    public static final String CACHE_KEY_PREFIX = "support";
    
    // Time formats
    public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    
    // Common regex patterns
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
    public static final String PHONE_REGEX = "^\\+?[1-9]\\d{1,14}$"; // E.164 format
    public static final String URL_REGEX = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
    public static final String HOSTNAME_REGEX = "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$";
    
    // Common field names
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_CREATED_AT = "createdAt";
    public static final String FIELD_UPDATED_AT = "updatedAt";
    public static final String FIELD_CREATED_BY = "createdBy";
    public static final String FIELD_UPDATED_BY = "updatedBy";
    
    // HTTP headers
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ACCEPT = "Accept";
    public static final String AUTHORIZATION = "Authorization";
    public static final String API_KEY_HEADER = "X-API-Key";
    
    // Content types
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String TEXT_PLAIN = "text/plain";
}