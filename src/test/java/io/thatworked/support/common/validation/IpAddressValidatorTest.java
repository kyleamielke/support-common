package io.thatworked.support.common.validation;

import io.thatworked.support.common.validation.impl.IpAddressValidator;
import io.thatworked.support.common.validation.annotations.ValidIpAddress;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IpAddressValidatorTest {
    
    private IpAddressValidator validator;
    
    @Mock
    private ValidIpAddress annotation;
    
    @Mock
    private ConstraintValidatorContext context;
    
    @BeforeEach
    void setUp() {
        validator = new IpAddressValidator();
    }
    
    @Test
    void shouldAcceptNullValue() {
        when(annotation.allowIpv4()).thenReturn(true);
        when(annotation.allowIpv6()).thenReturn(true);
        validator.initialize(annotation);
        
        assertThat(validator.isValid(null, context)).isTrue();
    }
    
    @Test
    void shouldAcceptEmptyValue() {
        when(annotation.allowIpv4()).thenReturn(true);
        when(annotation.allowIpv6()).thenReturn(true);
        validator.initialize(annotation);
        
        assertThat(validator.isValid("", context)).isTrue();
        assertThat(validator.isValid("  ", context)).isTrue();
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "192.168.1.1",
        "10.0.0.0",
        "172.16.0.1",
        "255.255.255.255",
        "0.0.0.0",
        "127.0.0.1"
    })
    void shouldAcceptValidIpv4Addresses(String ipAddress) {
        when(annotation.allowIpv4()).thenReturn(true);
        when(annotation.allowIpv6()).thenReturn(false);
        validator.initialize(annotation);
        
        assertThat(validator.isValid(ipAddress, context)).isTrue();
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "256.1.1.1",
        "1.256.1.1",
        "1.1.256.1",
        "1.1.1.256",
        "192.168.1",
        "192.168.1.1.1",
        "a.b.c.d",
        "192.168.-1.1"
    })
    void shouldRejectInvalidIpv4Addresses(String ipAddress) {
        when(annotation.allowIpv4()).thenReturn(true);
        when(annotation.allowIpv6()).thenReturn(false);
        validator.initialize(annotation);
        
        assertThat(validator.isValid(ipAddress, context)).isFalse();
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        "2001:0db8:85a3:0000:0000:8a2e:0370:7334",
        "2001:db8:85a3:0:0:8a2e:370:7334",
        "2001:db8:85a3::8a2e:370:7334",
        "::1",
        "::",
        "fe80::1",
        "::ffff:192.0.2.1"
    })
    void shouldAcceptValidIpv6Addresses(String ipAddress) {
        when(annotation.allowIpv4()).thenReturn(false);
        when(annotation.allowIpv6()).thenReturn(true);
        validator.initialize(annotation);
        
        assertThat(validator.isValid(ipAddress, context)).isTrue();
    }
    
    @Test
    void shouldRejectIpv4WhenOnlyIpv6Allowed() {
        when(annotation.allowIpv4()).thenReturn(false);
        when(annotation.allowIpv6()).thenReturn(true);
        validator.initialize(annotation);
        
        assertThat(validator.isValid("192.168.1.1", context)).isFalse();
    }
    
    @Test
    void shouldRejectIpv6WhenOnlyIpv4Allowed() {
        when(annotation.allowIpv4()).thenReturn(true);
        when(annotation.allowIpv6()).thenReturn(false);
        validator.initialize(annotation);
        
        assertThat(validator.isValid("2001:db8::1", context)).isFalse();
    }
}