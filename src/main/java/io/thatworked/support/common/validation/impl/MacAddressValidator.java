package io.thatworked.support.common.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Validator implementation for MAC address validation.
 * Supports common formats:
 * - XX:XX:XX:XX:XX:XX (colon-separated)
 * - XX-XX-XX-XX-XX-XX (hyphen-separated)
 * - XX.XX.XX.XX.XX.XX (dot-separated)
 * - XXXX.XXXX.XXXX (Cisco format)
 * - XXXXXX-XXXXXX (Microsoft format)
 * - XXXXXXXXXXXX (no separator)
 */
import io.thatworked.support.common.validation.annotations.ValidMacAddress;

public class MacAddressValidator implements ConstraintValidator<ValidMacAddress, String> {
    
    // Pre-compiled pattern for hex validation (fastest)
    private static final Pattern HEX_PATTERN = Pattern.compile("^[0-9A-F]{12}$");
    
    @Override
    public void initialize(ValidMacAddress constraintAnnotation) {
        // No initialization needed
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotNull handle null validation
        }
        
        // First check if it matches any valid format exactly
        if (!isValidFormat(value)) {
            return false;
        }
        
        // Remove all separators and convert to uppercase
        String normalized = value.toUpperCase().replaceAll("[.:-]", "");
        
        // A valid MAC address should have exactly 12 hex characters
        if (normalized.length() != 12) {
            return false;
        }
        
        // Check if all characters are valid hex (using pre-compiled pattern)
        return HEX_PATTERN.matcher(normalized).matches();
    }
    
    private boolean isValidFormat(String value) {
        // Check each known valid format
        return value.matches("^([0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$") ||     // XX:XX:XX:XX:XX:XX
               value.matches("^([0-9A-Fa-f]{2}-){5}[0-9A-Fa-f]{2}$") ||     // XX-XX-XX-XX-XX-XX
               value.matches("^([0-9A-Fa-f]{2}\\.){5}[0-9A-Fa-f]{2}$") ||   // XX.XX.XX.XX.XX.XX
               value.matches("^([0-9A-Fa-f]{4}\\.){2}[0-9A-Fa-f]{4}$") ||   // XXXX.XXXX.XXXX
               value.matches("^[0-9A-Fa-f]{6}-[0-9A-Fa-f]{6}$") ||          // XXXXXX-XXXXXX
               value.matches("^[0-9A-Fa-f]{12}$");                           // XXXXXXXXXXXX
    }
}