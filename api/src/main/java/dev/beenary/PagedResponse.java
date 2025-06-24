package dev.beenary;

import lombok.Data;

import java.util.List;

@Data
public class PagedResponse<T> {

    private List<T> content;

    private long totalElements;

    private int totalPages;

    public PagedResponse(final List<T> content, final long totalElements, final int totalPages) {
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static <T> PagedResponse<T> of(final List<T> content, final long totalElements, final int totalPages) {
        return new PagedResponse<>(content, totalElements, totalPages);
    }
}
