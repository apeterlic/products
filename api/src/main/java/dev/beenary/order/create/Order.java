package dev.beenary.order.create;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {

    @Schema(description = "Buyer's email.", requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "{order-email.invalid}")
    @NotBlank(message = "{order-email.empty}")
    private String email;

    @Schema(description = "Items that will be placed in order.", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    @NotNull(message = "{order-order-items.empty}")
    private List<OrderItem> orderItems;

    private LocalDateTime createdAt;
}
