package dev.beenary.api.product.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.beenary.api.PayloadRequest;
import dev.beenary.api.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Request for creating or updating a product.")
@NotNull(message = "{request.empty}")
public class UpdateProductRequest extends PayloadRequest<Product> {

    @JsonIgnore
    private UUID id;

}
