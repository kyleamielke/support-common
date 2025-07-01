package io.thatworked.support.common.util.string;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Common string manipulation utilities.
 */
public final class StringUtils {
    
    private StringUtils() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    /**
     * Check if a string is null or empty.
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    /**
     * Check if a string is null, empty, or contains only whitespace.
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Check if a string is not blank.
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    /**
     * Truncate a string to a maximum length.
     */
    public static String truncate(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }
    
    /**
     * Truncate a string with ellipsis.
     */
    public static String truncateWithEllipsis(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        if (maxLength < 4) {
            return truncate(str, maxLength);
        }
        return str.substring(0, maxLength - 3) + "...";
    }
    
    /**
     * Convert a string to camelCase.
     */
    public static String toCamelCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        
        String[] parts = str.split("[\\s_-]+");
        if (parts.length == 0) {
            return str.toLowerCase();
        }
        
        return parts[0].toLowerCase() + 
               Arrays.stream(parts, 1, parts.length)
                     .map(StringUtils::capitalize)
                     .collect(Collectors.joining());
    }
    
    /**
     * Convert a string to PascalCase.
     */
    public static String toPascalCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        
        return Arrays.stream(str.split("[\\s_-]+"))
                     .map(StringUtils::capitalize)
                     .collect(Collectors.joining());
    }
    
    /**
     * Convert a string to snake_case.
     */
    public static String toSnakeCase(String str) {
        if (isBlank(str)) {
            return str;
        }
        
        return str.replaceAll("([a-z])([A-Z])", "$1_$2")
                  .replaceAll("[\\s-]+", "_")
                  .toLowerCase();
    }
    
    /**
     * Convert a string to UPPER_SNAKE_CASE.
     */
    public static String toUpperSnakeCase(String str) {
        return toSnakeCase(str).toUpperCase();
    }
    
    /**
     * Capitalize the first letter of a string.
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
    
    /**
     * Remove all non-alphanumeric characters.
     */
    public static String sanitize(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }
    
    /**
     * Mask sensitive data (e.g., for logging).
     */
    public static String mask(String str, int visibleChars) {
        if (isEmpty(str) || str.length() <= visibleChars) {
            return str;
        }
        
        int maskLength = str.length() - visibleChars;
        return "*".repeat(maskLength) + str.substring(maskLength);
    }
    
    /**
     * Join strings with a delimiter, ignoring null/empty values.
     */
    public static String joinNonEmpty(String delimiter, String... parts) {
        return Arrays.stream(parts)
                     .filter(StringUtils::isNotBlank)
                     .collect(Collectors.joining(delimiter));
    }
}