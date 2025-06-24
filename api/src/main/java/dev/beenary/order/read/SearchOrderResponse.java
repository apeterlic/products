package dev.beenary.order.read;

import dev.beenary.PagedResponse;

import java.util.List;

public class SearchOrderResponse extends PagedResponse<OrderDetails> {
    public SearchOrderResponse(final List<OrderDetails> content, final long totalElements, final int totalPages) {
        super(content, totalElements, totalPages);
    }
}
