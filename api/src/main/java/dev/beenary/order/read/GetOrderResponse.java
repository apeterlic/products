package dev.beenary.order.read;

import dev.beenary.PayloadResponse;

public class GetOrderResponse extends PayloadResponse<OrderDetails> {
    public GetOrderResponse(final OrderDetails payload) {
        super(payload);
    }
}
