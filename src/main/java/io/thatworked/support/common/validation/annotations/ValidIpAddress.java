package io.thatworked.support.common.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validation annotation for IP addresses.
 * Validates both IPv4 and IPv6 addresses.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = io.thatworked.support.common.validation.impl.IpAddressValidator.class)
@Documented
public @interface ValidIpAddress {
    
    String message() default "Invalid IP address format";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    /**
     * Whether to allow IPv4 addresses.
     */
    boolean allowIpv4() default true;
    
    /**
     * Whether to allow IPv6 addresses.
     */
    boolean allowIpv6() default true;
}