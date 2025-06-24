package dev.beenary;

import lombok.Data;

@Data
public class PaginationAndSortingRequest {

    private PaginationFilter paginationFilter;

    private SortingFilter sortingFilter;

}
