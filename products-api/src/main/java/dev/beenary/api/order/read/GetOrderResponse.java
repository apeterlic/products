package dev.beenary.api.order.read;

import dev.beenary.api.PayloadResponse;

import java.io.Serial;

public class GetOrderResponse extends PayloadResponse<OrderDetails> {

    @Serial
    private static final long serialVersionUID = 1L;

    public GetOrderResponse(final OrderDetails payload) {
        super(payload);
    }
}
