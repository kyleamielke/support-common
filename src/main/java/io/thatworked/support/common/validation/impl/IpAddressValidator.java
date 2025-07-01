package io.thatworked.support.common.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Validator implementation for IP address validation.
 */
import io.thatworked.support.common.validation.annotations.ValidIpAddress;

public class IpAddressValidator implements ConstraintValidator<ValidIpAddress, String> {
    
    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$"
    );
    
    private static final Pattern IPV6_PATTERN = Pattern.compile(
        "^(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|" +
        "([0-9a-fA-F]{1,4}:){1,7}:|" +
        "([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|" +
        "([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|" +
        "([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|" +
        "([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|" +
        "([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|" +
        "[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|" +
        ":((:[0-9a-fA-F]{1,4}){1,7}|:)|" +
        "fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|" +
        "::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}" +
        "(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|" +
        "([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}" +
        "(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))$"
    );
    
    private boolean allowIpv4;
    private boolean allowIpv6;
    
    @Override
    public void initialize(ValidIpAddress constraintAnnotation) {
        this.allowIpv4 = constraintAnnotation.allowIpv4();
        this.allowIpv6 = constraintAnnotation.allowIpv6();
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) {
            return true; // Let @NotNull handle null validation
        }
        
        if (allowIpv4 && IPV4_PATTERN.matcher(value).matches()) {
            return true;
        }
        
        if (allowIpv6 && IPV6_PATTERN.matcher(value).matches()) {
            return true;
        }
        
        return false;
    }
}