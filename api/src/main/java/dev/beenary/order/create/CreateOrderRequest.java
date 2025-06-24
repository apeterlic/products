package dev.beenary.order.create;

import dev.beenary.PayloadRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request for creating new order.")
@NotNull(message = "{request.empty}")
public class CreateOrderRequest extends PayloadRequest<Order> {

}
