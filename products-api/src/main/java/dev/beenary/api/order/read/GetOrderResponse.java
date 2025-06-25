package dev.beenary.api.order.read;

import dev.beenary.api.PayloadResponse;

import java.io.Serial;

public class GetOrderResponse extends PayloadResponse<OrderDetail> {

    @Serial
    private static final long serialVersionUID = 1L;

    public GetOrderResponse(final OrderDetail payload) {
        super(payload);
    }
}
