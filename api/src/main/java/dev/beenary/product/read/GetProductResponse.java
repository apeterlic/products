package dev.beenary.product.read;

import dev.beenary.PayloadResponse;
import dev.beenary.product.Product;

public class GetProductResponse extends PayloadResponse<Product> {
    public GetProductResponse(final Product payload) {
        super(payload);
    }
}
