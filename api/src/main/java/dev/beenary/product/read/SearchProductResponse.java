package dev.beenary.product.read;

import dev.beenary.PagedResponse;
import dev.beenary.product.Product;

import java.util.List;

public class SearchProductResponse extends PagedResponse<Product> {

    public SearchProductResponse(final List<Product> content, final long totalElements, final int totalPages) {
        super(content, totalElements, totalPages);
    }
}
