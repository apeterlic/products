package dev.beenary.api.category.read;

import dev.beenary.api.PayloadResponse;
import dev.beenary.api.Resource;

import java.io.Serial;
import java.util.List;

public class GetCategoryResponse extends PayloadResponse<List<Resource>> {

    @Serial
    private static final long serialVersionUID = 1L;

    public GetCategoryResponse(final List<Resource> payload) {
        super(payload);
    }
}
