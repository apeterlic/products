package dev.beenary.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PayloadRequest<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Valid
    @NotNull(message = "{product.empty}")
    @Schema(description = "Product to be created or updated.", requiredMode = Schema.RequiredMode.REQUIRED)
    private T payload;
}
