package io.thatworked.support.common.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Validation annotation for email addresses.
 * More comprehensive than the standard @Email annotation.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = io.thatworked.support.common.validation.impl.EmailValidator.class)
@Documented
public @interface ValidEmail {
    
    String message() default "Invalid email address";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    /**
     * Whether to allow subdomains (e.g., user@mail.company.com).
     */
    boolean allowSubdomains() default true;
    
    /**
     * Whether to check if the domain has valid MX records.
     * Note: This requires network access and may slow down validation.
     */
    boolean checkMxRecords() default false;
}