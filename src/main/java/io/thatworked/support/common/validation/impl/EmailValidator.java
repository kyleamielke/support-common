package io.thatworked.support.common.validation.impl;

import io.thatworked.support.common.constants.CommonConstants;
import io.thatworked.support.common.validation.annotations.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Validator implementation for email validation.
 */

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(CommonConstants.EMAIL_REGEX);
    
    private boolean allowSubdomains;
    private boolean checkMxRecords;
    
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        this.allowSubdomains = constraintAnnotation.allowSubdomains();
        this.checkMxRecords = constraintAnnotation.checkMxRecords();
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotNull handle null validation
        }
        
        // Basic format validation
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            return false;
        }
        
        // Additional validations
        String[] parts = value.split("@");
        if (parts.length != 2) {
            return false;
        }
        
        String localPart = parts[0];
        String domainPart = parts[1];
        
        // Validate local part
        if (localPart.isEmpty() || localPart.length() > 64) {
            return false;
        }
        
        // Check for consecutive dots
        if (value.contains("..")) {
            return false;
        }
        
        // Check subdomain restriction
        if (!allowSubdomains && domainPart.split("\\.").length > 2) {
            return false;
        }
        
        // MX record check would go here if enabled
        // (skipped for performance in this implementation)
        
        return true;
    }
}