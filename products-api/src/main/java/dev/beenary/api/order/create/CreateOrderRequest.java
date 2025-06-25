package dev.beenary.api.order.create;

import dev.beenary.api.PayloadRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;

@Schema(description = "Request for creating new order.")
@NotNull(message = "{request.empty}")
public class CreateOrderRequest extends PayloadRequest<Order> {

    @Serial
    private static final long serialVersionUID = 1L;

}
