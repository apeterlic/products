package dev.beenary;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PayloadRequest<T> {

    @Valid
    @NotNull(message = "{product.empty}")
    @Schema(description = "Product to be created or updated.", requiredMode = Schema.RequiredMode.REQUIRED)
    private T payload;
}
