package dev.beenary.product.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.beenary.PayloadRequest;
import dev.beenary.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Request for creating or updating a product.")
@NotNull(message = "{request.empty}")
public class UpdateProductRequest extends PayloadRequest<Product> {

    @JsonIgnore
    private UUID id;

}
