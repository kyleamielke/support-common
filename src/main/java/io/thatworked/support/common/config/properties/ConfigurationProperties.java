package io.thatworked.support.common.config.properties;

import java.lang.annotation.*;

/**
 * Marks a class as configuration properties.
 * Similar to Spring's @ConfigurationProperties but framework-agnostic.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigurationProperties {
    
    /**
     * The prefix of the properties to bind.
     */
    String prefix() default "";
    
    /**
     * Flag to ignore invalid fields during binding.
     */
    boolean ignoreInvalidFields() default false;
    
    /**
     * Flag to ignore unknown fields during binding.
     */
    boolean ignoreUnknownFields() default true;
}