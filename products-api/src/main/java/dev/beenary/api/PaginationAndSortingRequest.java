package dev.beenary.api;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PaginationAndSortingRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private PaginationFilter paginationFilter;

    private SortingFilter sortingFilter;

}
