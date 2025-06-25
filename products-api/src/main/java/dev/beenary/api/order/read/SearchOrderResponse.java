package dev.beenary.api.order.read;

import dev.beenary.api.PagedResponse;

import java.io.Serial;
import java.util.List;

public class SearchOrderResponse extends PagedResponse<OrderDetail> {

    @Serial
    private static final long serialVersionUID = 1L;

    public SearchOrderResponse(final List<OrderDetail> content, final long totalElements, final int totalPages) {
        super(content, totalElements, totalPages);
    }
}
