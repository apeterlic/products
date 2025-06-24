package dev.beenary.category;

import dev.beenary.PayloadResponse;
import dev.beenary.Resource;

import java.util.List;

public class GetCategoryResponse extends PayloadResponse<List<Resource>> {

    public GetCategoryResponse(final List<Resource> payload) {
        super(payload);
    }
}
