package dev.beenary.api.product.read;

import dev.beenary.api.PayloadResponse;
import dev.beenary.api.product.Product;

import java.io.Serial;

public class GetProductResponse extends PayloadResponse<Product> {

    @Serial
    private static final long serialVersionUID = 1L;

    public GetProductResponse(final Product payload) {
        super(payload);
    }
}
