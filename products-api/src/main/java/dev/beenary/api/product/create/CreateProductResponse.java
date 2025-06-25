package dev.beenary.api.product.create;

import dev.beenary.api.IdResponse;

import java.io.Serial;
import java.util.UUID;


public class CreateProductResponse extends IdResponse {

    @Serial
    private static final long serialVersionUID = 1L;

    public CreateProductResponse(final UUID id) {
        super(id);
    }
}
