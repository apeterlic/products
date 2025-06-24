package dev.beenary.product.create;

import dev.beenary.PayloadRequest;
import dev.beenary.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Request for creating or updating a product.")
@NotNull(message = "{request.empty}")
@Data
public class CreateProductRequest extends PayloadRequest<Product> {

}
