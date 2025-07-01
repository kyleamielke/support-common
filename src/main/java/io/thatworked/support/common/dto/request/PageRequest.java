package io.thatworked.support.common.dto.request;

import io.thatworked.support.common.enums.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Standard pagination request DTO.
 * Use this for all paginated endpoints across services.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
    
    /**
     * Page number (0-based).
     */
    @Min(0)
    @Builder.Default
    private int page = 0;
    
    /**
     * Number of items per page.
     */
    @Min(1)
    @Max(100)
    @Builder.Default
    private int size = 20;
    
    /**
     * Field to sort by.
     */
    @Builder.Default
    private String sortBy = "id";
    
    /**
     * Sort direction.
     */
    @Builder.Default
    private SortDirection sortDirection = SortDirection.ASC;
    
    /**
     * Convert to Spring Data Pageable.
     * Note: This method is optional and only available when Spring Data is on classpath.
     */
    public org.springframework.data.domain.Pageable toPageable() {
        return org.springframework.data.domain.PageRequest.of(
            page, 
            size,
            sortDirection == SortDirection.ASC 
                ? org.springframework.data.domain.Sort.Direction.ASC 
                : org.springframework.data.domain.Sort.Direction.DESC,
            sortBy
        );
    }
}