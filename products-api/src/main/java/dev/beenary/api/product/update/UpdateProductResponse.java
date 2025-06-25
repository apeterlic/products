package dev.beenary.api.product.update;

import dev.beenary.api.PayloadResponse;
import dev.beenary.api.product.Product;

import java.io.Serial;


public class UpdateProductResponse extends PayloadResponse<Product> {

    @Serial
    private static final long serialVersionUID = 1L;

    public UpdateProductResponse(final Product payload) {
        super(payload);
    }
}
