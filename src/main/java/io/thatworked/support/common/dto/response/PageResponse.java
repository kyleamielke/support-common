package io.thatworked.support.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Standard pagination response DTO.
 * Use this for all paginated responses across services.
 * 
 * @param <T> The type of content in the page
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    
    /**
     * The content of the current page.
     */
    private List<T> content;
    
    /**
     * Current page number (0-based).
     */
    private int pageNumber;
    
    /**
     * Size of the page.
     */
    private int pageSize;
    
    /**
     * Total number of elements across all pages.
     */
    private long totalElements;
    
    /**
     * Total number of pages.
     */
    private int totalPages;
    
    /**
     * Whether this is the first page.
     */
    private boolean first;
    
    /**
     * Whether this is the last page.
     */
    private boolean last;
    
    /**
     * Create a PageResponse from Spring Data Page.
     * Note: This method is optional and only available when Spring Data is on classpath.
     */
    public static <T> PageResponse<T> fromPage(org.springframework.data.domain.Page<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}