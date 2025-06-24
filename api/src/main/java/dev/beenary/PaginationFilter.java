package dev.beenary;

import lombok.Data;

@Data
public class PaginationFilter {
    public static final int DEFAULT_ENTITIES_PER_PAGE = 10;
    public static final int DEFAULT_PAGE = 1;

    private int entitiesPerPage;

    private int page;

    public PaginationFilter() {
        this.entitiesPerPage = DEFAULT_ENTITIES_PER_PAGE;
        this.page = DEFAULT_PAGE;
    }

    public PaginationFilter(final int entitiesPerPage, final int page) {
        this.entitiesPerPage = entitiesPerPage;
        this.page = page;
    }
}
