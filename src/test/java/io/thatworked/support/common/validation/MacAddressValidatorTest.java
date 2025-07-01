package io.thatworked.support.common.validation;

import io.thatworked.support.common.validation.impl.MacAddressValidator;
import io.thatworked.support.common.validation.annotations.ValidMacAddress;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MacAddressValidatorTest {
    
    private MacAddressValidator validator;
    
    @Mock
    private ValidMacAddress annotation;
    
    @Mock
    private ConstraintValidatorContext context;
    
    @BeforeEach
    void setUp() {
        validator = new MacAddressValidator();
        validator.initialize(annotation);
    }
    
    @Test
    void shouldAcceptNullValue() {
        assertThat(validator.isValid(null, context)).isTrue();
    }
    
    @Test
    void shouldAcceptEmptyValue() {
        assertThat(validator.isValid("", context)).isTrue();
        assertThat(validator.isValid("  ", context)).isTrue();
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        // Colon-separated
        "00:11:22:33:44:55",
        "AA:BB:CC:DD:EE:FF",
        "aa:bb:cc:dd:ee:ff",
        
        // Hyphen-separated
        "00-11-22-33-44-55",
        "AA-BB-CC-DD-EE-FF",
        
        // Dot-separated
        "00.11.22.33.44.55",
        "AA.BB.CC.DD.EE.FF",
        
        // Cisco format
        "0011.2233.4455",
        "AABB.CCDD.EEFF",
        "aabb.ccdd.eeff",
        
        // Microsoft format
        "001122-334455",
        "AABBCC-DDEEFF",
        
        // No separator
        "001122334455",
        "AABBCCDDEEFF",
        "aabbccddeeff"
    })
    void shouldAcceptValidMacAddresses(String macAddress) {
        assertThat(validator.isValid(macAddress, context)).isTrue();
    }
    
    @ParameterizedTest
    @ValueSource(strings = {
        // Too short
        "00:11:22:33:44",
        "00112233445",
        
        // Too long
        "00:11:22:33:44:55:66",
        "00112233445566",
        
        // Invalid characters
        "00:11:22:33:44:GG",
        "ZZ:11:22:33:44:55",
        "00:11:22:33:44:5X",
        
        // Wrong format
        "00:11:22:33:44:55:66",
        "00:11:22:33:44",
        "00:1122:33:44:55",
        "0011.2233.4455.6677",
        
        // Mixed separators
        "00:11-22:33-44:55",
        "00.11:22.33:44.55"
    })
    void shouldRejectInvalidMacAddresses(String macAddress) {
        assertThat(validator.isValid(macAddress, context)).isFalse();
    }
    
    @Test
    void shouldBeCaseInsensitive() {
        assertThat(validator.isValid("aa:bb:cc:dd:ee:ff", context)).isTrue();
        assertThat(validator.isValid("AA:BB:CC:DD:EE:FF", context)).isTrue();
        assertThat(validator.isValid("Aa:Bb:Cc:Dd:Ee:Ff", context)).isTrue();
    }
}