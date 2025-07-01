package io.thatworked.support.common.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validation annotation for MAC addresses.
 * Supports common MAC address formats.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = io.thatworked.support.common.validation.impl.MacAddressValidator.class)
@Documented
public @interface ValidMacAddress {
    
    String message() default "Invalid MAC address format";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}