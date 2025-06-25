package dev.beenary.api;

import dev.beenary.common.utility.SortDirection;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SortingFilter implements Serializable {

    public static final String DEFAULT_SORT_COLUMN = "id";
    public static final String DEFAULT_SORT_DIRECTION = "DESC";

    @Serial
    private static final long serialVersionUID = 1L;

    private String column;

    private SortDirection sortDirection;

    public SortingFilter() {
        this.column = DEFAULT_SORT_COLUMN;
        this.sortDirection = SortDirection.DESC;
    }

    public SortingFilter(final String column, final SortDirection sortDirection) {
        this.column = column;
        this.sortDirection = sortDirection;
    }
}
