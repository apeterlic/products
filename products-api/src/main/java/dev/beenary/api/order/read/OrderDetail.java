package dev.beenary.api.order.read;

import dev.beenary.api.ApiIdEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDetail extends ApiIdEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Buyer's email.", requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "{order-email.invalid}")
    @NotBlank(message = "{order-email.empty}")
    private String email;

    @Schema(description = "Items that will be placed in order.", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    @NotNull(message = "{order-order-items.empty}")
    private List<OrderItemDetail> orderItems;

    private LocalDateTime createdAt;

    private BigDecimal totalPrice;

}
