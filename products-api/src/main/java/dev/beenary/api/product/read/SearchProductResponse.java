package dev.beenary.api.product.read;

import dev.beenary.api.PagedResponse;
import dev.beenary.api.product.Product;

import java.io.Serial;
import java.util.List;

public class SearchProductResponse extends PagedResponse<Product> {

    @Serial
    private static final long serialVersionUID = 1L;

    public SearchProductResponse(final List<Product> content, final long totalElements, final int totalPages) {
        super(content, totalElements, totalPages);
    }
}
