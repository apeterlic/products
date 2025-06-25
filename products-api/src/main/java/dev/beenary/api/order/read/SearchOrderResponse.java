package dev.beenary.api.order.read;

import dev.beenary.api.PagedResponse;

import java.io.Serial;
import java.util.List;

public class SearchOrderResponse extends PagedResponse<OrderDetails> {

    @Serial
    private static final long serialVersionUID = 1L;

    public SearchOrderResponse(final List<OrderDetails> content, final long totalElements, final int totalPages) {
        super(content, totalElements, totalPages);
    }
}
