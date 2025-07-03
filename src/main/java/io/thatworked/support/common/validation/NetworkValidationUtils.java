package io.thatworked.support.common.validation;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * Utility class for network-related validations.
 * Provides common validation methods for IP addresses, MAC addresses, etc.
 */
@UtilityClass
public class NetworkValidationUtils {
    
    private static final String IP_ADDRESS_PATTERN = 
        "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    
    private static final String MAC_ADDRESS_PATTERN = 
        "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";
    
    private static final Pattern IP_PATTERN_COMPILED = Pattern.compile(IP_ADDRESS_PATTERN);
    private static final Pattern MAC_PATTERN_COMPILED = Pattern.compile(MAC_ADDRESS_PATTERN);
    
    /**
     * Validates if the given string is a valid IPv4 address.
     *
     * @param ipAddress the IP address to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidIpAddress(String ipAddress) {
        if (ipAddress == null || ipAddress.trim().isEmpty()) {
            return false;
        }
        return IP_PATTERN_COMPILED.matcher(ipAddress).matches();
    }
    
    /**
     * Validates if the given string is a valid MAC address.
     * Accepts both colon (:) and hyphen (-) separators.
     *
     * @param macAddress the MAC address to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidMacAddress(String macAddress) {
        if (macAddress == null || macAddress.trim().isEmpty()) {
            return false;
        }
        return MAC_PATTERN_COMPILED.matcher(macAddress).matches();
    }
    
    /**
     * Normalizes a MAC address to use uppercase letters and colon separators.
     *
     * @param macAddress the MAC address to normalize
     * @return normalized MAC address or null if input is null
     */
    public static String normalizeMacAddress(String macAddress) {
        if (macAddress == null) {
            return null;
        }
        // Convert to uppercase and use colon as separator
        return macAddress.toUpperCase().replace("-", ":");
    }
    
    /**
     * Validates if the given string is a valid hostname.
     *
     * @param hostname the hostname to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidHostname(String hostname) {
        if (hostname == null || hostname.trim().isEmpty()) {
            return false;
        }
        
        // Basic hostname validation - can be enhanced as needed
        String hostnamePattern = "^[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(\\.[a-zA-Z0-9]([a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        return hostname.matches(hostnamePattern);
    }
}