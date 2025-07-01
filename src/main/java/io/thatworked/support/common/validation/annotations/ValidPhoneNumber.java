package io.thatworked.support.common.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validation annotation for phone numbers.
 * Supports E.164 format and common variations.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = io.thatworked.support.common.validation.impl.PhoneNumberValidator.class)
@Documented
public @interface ValidPhoneNumber {
    
    String message() default "Invalid phone number format";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    /**
     * Whether to require E.164 format (+1234567890).
     */
    boolean requireE164Format() default false;
    
    /**
     * Whether to allow extensions (e.g., +1234567890x123).
     */
    boolean allowExtensions() default true;
}