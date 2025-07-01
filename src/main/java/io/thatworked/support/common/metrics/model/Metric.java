package io.thatworked.support.common.metrics.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

/**
 * Metric data point.
 */
@Data
@Builder
public class Metric {
    
    /**
     * Metric name.
     */
    private String name;
    
    /**
     * Metric type.
     */
    private io.thatworked.support.common.metrics.types.MetricType type;
    
    /**
     * Metric value.
     */
    private Double value;
    
    /**
     * Metric unit.
     */
    private String unit;
    
    /**
     * Timestamp.
     */
    @Builder.Default
    private Instant timestamp = Instant.now();
    
    /**
     * Metric tags/labels.
     */
    private Map<String, String> tags;
    
    /**
     * Description.
     */
    private String description;
    
    /**
     * Create a counter metric.
     */
    public static Metric counter(String name, double value, Map<String, String> tags) {
        return Metric.builder()
                .name(name)
                .type(io.thatworked.support.common.metrics.types.MetricType.COUNTER)
                .value(value)
                .tags(tags)
                .build();
    }
    
    /**
     * Create a gauge metric.
     */
    public static Metric gauge(String name, double value, Map<String, String> tags) {
        return Metric.builder()
                .name(name)
                .type(io.thatworked.support.common.metrics.types.MetricType.GAUGE)
                .value(value)
                .tags(tags)
                .build();
    }
    
    /**
     * Create a timer metric.
     */
    public static Metric timer(String name, double value, String unit, Map<String, String> tags) {
        return Metric.builder()
                .name(name)
                .type(io.thatworked.support.common.metrics.types.MetricType.TIMER)
                .value(value)
                .unit(unit)
                .tags(tags)
                .build();
    }
}