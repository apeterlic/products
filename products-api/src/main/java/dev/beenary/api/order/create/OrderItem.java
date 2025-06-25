package dev.beenary.api.order.create;

import dev.beenary.api.ApiIdEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItem extends ApiIdEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Product ID.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "{order-items-product.empty}")
    private UUID productId;

    @Schema(description = "Quantity.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "{order-items-quantity.empty}")
    @Min(value = 1, message = "{order-items-quantity.min-value}")
    private Integer quantity;
}
