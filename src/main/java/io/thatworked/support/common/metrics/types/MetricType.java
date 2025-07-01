package io.thatworked.support.common.metrics.types;

/**
 * Types of metrics.
 */
public enum MetricType {
    /**
     * Counter - monotonically increasing value.
     */
    COUNTER,
    
    /**
     * Gauge - point-in-time value.
     */
    GAUGE,
    
    /**
     * Histogram - distribution of values.
     */
    HISTOGRAM,
    
    /**
     * Timer - measures duration.
     */
    TIMER,
    
    /**
     * Summary - statistical distribution.
     */
    SUMMARY
}