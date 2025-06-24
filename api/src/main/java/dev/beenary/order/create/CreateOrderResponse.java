package dev.beenary.order.create;

import dev.beenary.PayloadResponse;
import dev.beenary.order.read.OrderDetails;

public class CreateOrderResponse extends PayloadResponse<OrderDetails> {
    public CreateOrderResponse(final OrderDetails payload) {
        super(payload);
    }
}
