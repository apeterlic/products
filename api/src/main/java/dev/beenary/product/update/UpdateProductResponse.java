package dev.beenary.product.update;

import dev.beenary.PayloadResponse;
import dev.beenary.product.Product;


public class UpdateProductResponse extends PayloadResponse<Product> {
    public UpdateProductResponse(final Product payload) {
        super(payload);
    }
}
