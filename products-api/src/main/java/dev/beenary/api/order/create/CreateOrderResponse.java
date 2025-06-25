package dev.beenary.api.order.create;

import dev.beenary.api.PayloadResponse;
import dev.beenary.api.order.read.OrderDetail;

import java.io.Serial;

public class CreateOrderResponse extends PayloadResponse<OrderDetail> {
    @Serial
    private static final long serialVersionUID = 1L;

    public CreateOrderResponse(final OrderDetail payload) {
        super(payload);
    }
}
