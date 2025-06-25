package dev.beenary.api.order.create;

import dev.beenary.api.PayloadResponse;
import dev.beenary.api.order.read.OrderDetails;

import java.io.Serial;

public class CreateOrderResponse extends PayloadResponse<OrderDetails> {
    @Serial
    private static final long serialVersionUID = 1L;

    public CreateOrderResponse(final OrderDetails payload) {
        super(payload);
    }
}
