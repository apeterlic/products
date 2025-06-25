package dev.beenary.api.order.read;

import dev.beenary.api.ApiIdEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderItemDetails extends ApiIdEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique product code.", requiredMode = Schema.RequiredMode.REQUIRED, example = "DM001")
    @NotNull(message = "{product-unique-code.empty}")
    private String code;

    @Schema(description = "Product name.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Disney mug")
    @NotBlank(message = "{product-name.empty}")
    private String name;

    @Schema(description = "Product description.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Modern and durable mug for children.")
    @NotBlank(message = "{product-description.empty}")
    private String description;

    @Schema(description = "Currency.", defaultValue = "EUR", requiredMode = Schema.RequiredMode.REQUIRED, example = "EUR")
    private String currency;

    @Schema(description = "Price without VAT.", defaultValue = "0.00", requiredMode = Schema.RequiredMode.REQUIRED, example = "15.46")
    @DecimalMin(value = "0.01", inclusive = false, message = "{product-price.min-value}")
    @NotNull(message = "{product-price.empty}")
    private BigDecimal price;

    @Schema(description = "VAT (percentage).", requiredMode = Schema.RequiredMode.REQUIRED, example = "25")
    @NotNull(message = "{product-vat.empty}")
    private Integer vat;

    @Schema(description = "Product type. Possible values: DIGITAL, PHYSICAL.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PHYSICAL")
    @NotNull(message = "{product-category.empty}")
    private String category;

    private Integer quantity;

    private BigDecimal totalPrice;
}
