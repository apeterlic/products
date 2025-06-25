package dev.beenary.api.product.delete;

import dev.beenary.api.IdRequest;

import java.io.Serial;
import java.util.UUID;

public class DeleteProductRequest extends IdRequest {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Factory method to create a new {@code DeleteAuthorityRequest} for the specified authority ID.
     *
     * @param id the UUID of the authority to be deleted
     * @return a new {@code DeleteAuthorityRequest} instance
     */
    public static DeleteProductRequest from(final UUID id) {
        final DeleteProductRequest request = new DeleteProductRequest();
        request.setId(id);
        return request;
    }
}
