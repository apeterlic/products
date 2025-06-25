package dev.beenary.api.product.delete;

import dev.beenary.api.PayloadResponse;

import java.io.Serial;

public class DeleteProductResponse extends PayloadResponse<Boolean> {

    @Serial
    private static final long serialVersionUID = 1L;

    public DeleteProductResponse(final Boolean payload) {
        super(payload);
    }
}
