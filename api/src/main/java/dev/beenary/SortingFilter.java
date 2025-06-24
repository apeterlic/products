package dev.beenary;

import dev.beenary.common.utility.SortDirection;
import lombok.Data;

@Data
public class SortingFilter {

    public static final String DEFAULT_SORT_COLUMN = "ID";
    public static final String DEFAULT_SORT_DIRECTION = "DESC";

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
