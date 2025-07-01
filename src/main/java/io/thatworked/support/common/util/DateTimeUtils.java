package io.thatworked.support.common.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for consistent date/time handling across services.
 */
public final class DateTimeUtils {
    
    /**
     * ISO-8601 formatter for consistent string representation.
     */
    public static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_INSTANT;
    
    /**
     * Human-readable date format.
     */
    public static final DateTimeFormatter HUMAN_READABLE_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.of("UTC"));
    
    private DateTimeUtils() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    /**
     * Convert Instant to ISO-8601 string.
     */
    public static String toIsoString(Instant instant) {
        if (instant == null) {
            return null;
        }
        return ISO_FORMATTER.format(instant);
    }
    
    /**
     * Parse ISO-8601 string to Instant.
     */
    public static Instant fromIsoString(String isoString) {
        if (isoString == null || isoString.trim().isEmpty()) {
            return null;
        }
        return Instant.parse(isoString);
    }
    
    /**
     * Convert Instant to human-readable format.
     */
    public static String toHumanReadable(Instant instant) {
        if (instant == null) {
            return null;
        }
        return HUMAN_READABLE_FORMATTER.format(instant);
    }
    
    /**
     * Get current timestamp.
     */
    public static Instant now() {
        return Instant.now();
    }
    
    /**
     * Check if a timestamp is in the past.
     */
    public static boolean isPast(Instant instant) {
        return instant != null && instant.isBefore(now());
    }
    
    /**
     * Check if a timestamp is in the future.
     */
    public static boolean isFuture(Instant instant) {
        return instant != null && instant.isAfter(now());
    }
}