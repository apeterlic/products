package dev.beenary.product.delete;

import dev.beenary.PayloadResponse;
import dev.beenary.product.Product;

public class DeleteProductResponse extends PayloadResponse<Product> {

    public DeleteProductResponse(final Product payload) {
        super(payload);
    }
}
