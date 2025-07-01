package io.thatworked.support.common.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Validator implementation for phone number validation.
 */
import io.thatworked.support.common.validation.annotations.ValidPhoneNumber;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    
    // E.164 format: +[country code][number] (1-15 digits total)
    private static final Pattern E164_PATTERN = Pattern.compile("^\\+[1-9]\\d{1,14}$");
    
    // More flexible pattern allowing common formats
    private static final Pattern FLEXIBLE_PATTERN = Pattern.compile(
        "^[+]?[(]?[0-9]{1,4}[)]?[-\\s\\.]?[(]?[0-9]{1,4}[)]?[-\\s\\.]?[0-9]{1,9}$"
    );
    
    private boolean requireE164Format;
    private boolean allowExtensions;
    
    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        this.requireE164Format = constraintAnnotation.requireE164Format();
        this.allowExtensions = constraintAnnotation.allowExtensions();
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotNull handle null validation
        }
        
        String phoneNumber = value.trim();
        String extension = null;
        
        // Check for extension
        if (allowExtensions) {
            int extIndex = phoneNumber.toLowerCase().indexOf("x");
            if (extIndex == -1) {
                extIndex = phoneNumber.toLowerCase().indexOf("ext");
            }
            
            if (extIndex > 0) {
                extension = phoneNumber.substring(extIndex);
                phoneNumber = phoneNumber.substring(0, extIndex).trim();
                
                // Validate extension format
                if (!extension.matches("^(x|ext\\.?)\\s*\\d{1,6}$")) {
                    return false;
                }
            }
        }
        
        // Remove all non-digit characters except + at the beginning
        String digitsOnly = phoneNumber.replaceAll("[^0-9+]", "");
        
        // Check length (minimum 7 digits, maximum 15 for E.164)
        int digitCount = digitsOnly.replace("+", "").length();
        if (digitCount < 7 || digitCount > 15) {
            return false;
        }
        
        // Apply format validation
        if (requireE164Format) {
            return E164_PATTERN.matcher(digitsOnly).matches();
        } else {
            return FLEXIBLE_PATTERN.matcher(phoneNumber).matches();
        }
    }
}