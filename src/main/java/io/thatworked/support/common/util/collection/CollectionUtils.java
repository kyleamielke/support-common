package io.thatworked.support.common.util.collection;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Common collection manipulation utilities.
 */
public final class CollectionUtils {
    
    private CollectionUtils() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    /**
     * Check if a collection is null or empty.
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    
    /**
     * Check if a collection is not empty.
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
    
    /**
     * Check if a map is null or empty.
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
    
    /**
     * Check if a map is not empty.
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
    
    /**
     * Safely get the size of a collection (returns 0 for null).
     */
    public static int size(Collection<?> collection) {
        return collection == null ? 0 : collection.size();
    }
    
    /**
     * Convert a collection to a list, handling null input.
     */
    public static <T> List<T> toList(Collection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }
        return collection instanceof List ? (List<T>) collection : new ArrayList<>(collection);
    }
    
    /**
     * Convert a collection to a set, handling null input.
     */
    public static <T> Set<T> toSet(Collection<T> collection) {
        if (collection == null) {
            return new HashSet<>();
        }
        return collection instanceof Set ? (Set<T>) collection : new HashSet<>(collection);
    }
    
    /**
     * Partition a list into chunks of specified size.
     */
    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (isEmpty(list) || size <= 0) {
            return new ArrayList<>();
        }
        
        List<List<T>> partitions = new ArrayList<>();
        for (int i = 0; i < list.size(); i += size) {
            partitions.add(list.subList(i, Math.min(i + size, list.size())));
        }
        return partitions;
    }
    
    /**
     * Remove duplicates from a list while preserving order.
     */
    public static <T> List<T> removeDuplicates(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(new LinkedHashSet<>(list));
    }
    
    /**
     * Find duplicates in a collection.
     */
    public static <T> Set<T> findDuplicates(Collection<T> collection) {
        if (isEmpty(collection)) {
            return new HashSet<>();
        }
        
        Set<T> seen = new HashSet<>();
        Set<T> duplicates = new HashSet<>();
        
        for (T item : collection) {
            if (!seen.add(item)) {
                duplicates.add(item);
            }
        }
        
        return duplicates;
    }
    
    /**
     * Convert a collection to a map using key extractor.
     */
    public static <K, V> Map<K, V> toMap(Collection<V> collection, Function<V, K> keyExtractor) {
        if (isEmpty(collection)) {
            return new HashMap<>();
        }
        
        return collection.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                    keyExtractor,
                    Function.identity(),
                    (existing, replacement) -> existing
                ));
    }
    
    /**
     * Group collection elements by a key.
     */
    public static <K, V> Map<K, List<V>> groupBy(Collection<V> collection, Function<V, K> classifier) {
        if (isEmpty(collection)) {
            return new HashMap<>();
        }
        
        return collection.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(classifier));
    }
    
    /**
     * Safely get first element from a collection.
     */
    public static <T> Optional<T> getFirst(Collection<T> collection) {
        if (isEmpty(collection)) {
            return Optional.empty();
        }
        return collection.stream().findFirst();
    }
    
    /**
     * Safely get last element from a list.
     */
    public static <T> Optional<T> getLast(List<T> list) {
        if (isEmpty(list)) {
            return Optional.empty();
        }
        return Optional.of(list.get(list.size() - 1));
    }
    
    /**
     * Create an unmodifiable copy of a collection.
     */
    public static <T> List<T> unmodifiableList(Collection<T> collection) {
        if (collection == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(toList(collection));
    }
    
    /**
     * Create an unmodifiable copy of a set.
     */
    public static <T> Set<T> unmodifiableSet(Collection<T> collection) {
        if (collection == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(toSet(collection));
    }
}