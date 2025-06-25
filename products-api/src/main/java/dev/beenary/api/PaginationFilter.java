package dev.beenary.api;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PaginationFilter implements Serializable {

    public static final int DEFAULT_ENTITIES_PER_PAGE = 10;
    public static final int DEFAULT_PAGE = 1;

    @Serial
    private static final long serialVersionUID = 1L;

    private int entitiesPerPage;

    private int page;

    public PaginationFilter() {
        this.entitiesPerPage = DEFAULT_ENTITIES_PER_PAGE;
        this.page = DEFAULT_PAGE;
    }

    public PaginationFilter(final int page, final int entitiesPerPage) {
        this.entitiesPerPage = entitiesPerPage;
        this.page = page;
    }
}
