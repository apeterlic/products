package dev.beenary.api.product;

import dev.beenary.api.ApiIdEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class Product extends ApiIdEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Unique product code (Required).
     */
    @Schema(description = "Unique product code.", requiredMode = Schema.RequiredMode.REQUIRED, example = "DM001")
    @NotNull(message = "{product-unique-code.empty}")
    private String code;

    /**
     * Product name (Required).
     */
    @Schema(description = "Product name.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Disney mug")
    @NotBlank(message = "{product-name.empty}")
    private String name;

    /**
     * Product description (Required).
     */
    @Schema(description = "Product description.", requiredMode = Schema.RequiredMode.REQUIRED, example = "Modern and durable mug for children.")
    @NotBlank(message = "{product-description.empty}")
    private String description;

    /**
     * Currency (Required).
     */
    @Schema(description = "Currency.", defaultValue = "EUR", requiredMode = Schema.RequiredMode.REQUIRED, example = "EUR")
    @NotBlank(message = "{product-currency.empty}")
    private String currency;

    /**
     * Product price without VAT (Required).
     */
    @Schema(description = "Price without VAT.", defaultValue = "0.00", requiredMode = Schema.RequiredMode.REQUIRED, example = "15.46")
    @DecimalMin(value = "0.01", inclusive = false, message = "{product-price.min-value}")
    @NotNull(message = "{product-price.empty}")
    private BigDecimal price;

    /**
     * VAT (Required).
     */
    @Schema(description = "VAT (percentage).", requiredMode = Schema.RequiredMode.REQUIRED, example = "25")
    @NotNull(message = "{product-vat.empty}")
    private Integer vat;

    /**
     * Product category (Required).
     */
    @Schema(description = "Product type. Possible values: DIGITAL, PHYSICAL.", requiredMode = Schema.RequiredMode.REQUIRED, example = "PHYSICAL")
    @NotNull(message = "{product-category.empty}")
    private String category;

    /**
     * Product stock quantity (Optional). Required only for PHYSICAL product type.
     */
    @Schema(description = "Stock quantity. Required for PHYSICAL product type.", example = "10")
    private Long stockQuantity;

    /**
     * Information if product is enabled (Required).
     */
    @Schema(description = "Defines weather the product will be available for purchase or not.", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull
    private Boolean enabled;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
