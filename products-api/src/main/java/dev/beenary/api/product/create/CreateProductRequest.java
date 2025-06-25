package dev.beenary.api.product.create;

import dev.beenary.api.PayloadRequest;
import dev.beenary.api.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "Request for creating or updating a product.")
@NotNull(message = "{request.empty}")
@Data
public class CreateProductRequest extends PayloadRequest<Product> {

    @Serial
    private static final long serialVersionUID = 1L;

}
