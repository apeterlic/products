package dev.beenary.web.product;

import dev.beenary.api.product.create.CreateProductRequest;
import dev.beenary.api.product.create.CreateProductResponse;
import dev.beenary.api.product.delete.DeleteProductResponse;
import dev.beenary.api.product.read.GetProductResponse;
import dev.beenary.api.product.read.SearchProductResponse;
import dev.beenary.api.product.update.UpdateProductRequest;
import dev.beenary.api.product.update.UpdateProductResponse;
import dev.beenary.common.utility.SortDirection;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

import static dev.beenary.api.SortingFilter.DEFAULT_SORT_COLUMN;
import static dev.beenary.api.SortingFilter.DEFAULT_SORT_DIRECTION;

/**
 * Provides REST API endpoints for managing products.
 *
 * @author anapeterlic
 * @since 1.0
 */
@RequestMapping("/v1/products")
public interface ProductControllerDefinition {

    /**
     * Retrieves product details by ID.
     *
     * @param id [{@link UUID}] :: the product ID.
     * @return response [{@link GetProductResponse}] :: the product details.
     * @throws dev.beenary.common.exception.BusinessException in case of failure.
     */
    @Operation(summary = "Gets product based on the provided ID.")
    @GetMapping("/{id}")
    ResponseEntity<GetProductResponse> get(@PathVariable(name = "id") final UUID id);

    /**
     * Creates new product.
     *
     * @param request [{@link CreateProductRequest}] :: the product creation request
     * @return response [{@link CreateProductResponse}] :: the created product
     */
    @Operation(summary = "Creates product based on the provided request.")
    @PostMapping
    ResponseEntity<CreateProductResponse> create(@RequestBody @Valid final CreateProductRequest request);

    /**
     * Updates an existing product.
     *
     * @param id      [{@link UUID}] :: the product ID
     * @param request [{@link UpdateProductRequest}] :: the update request
     * @return response [{@link UpdateProductResponse}] :: the updated product
     */
    @Operation(summary = "Updates product based on the provided ID and request.")
    @PutMapping("/{id}")
    ResponseEntity<UpdateProductResponse> update(@PathVariable(name = "id") final UUID id,
                                                 @RequestBody @Valid final UpdateProductRequest request);

    /**
     * Deletes a product.
     *
     * @param id [{@link UUID}] :: the product ID
     * @return response [{@link DeleteProductResponse}] :: the deletion result
     */
    @Operation(summary = "Deletes product based on the provided ID.")
    @DeleteMapping("/{id}")
    ResponseEntity<DeleteProductResponse> delete(@PathVariable(name = "id") final UUID id);

    /**
     * Searches for authorities based on various criteria.
     *
     * @param size          [{@link Integer}] :: number of entities per page (optional)
     * @param page          [{@link Integer}] :: page number (optional)
     * @param sortBy        [{@link String}] :: sort column (optional)
     * @param sortDirection [{@link SortingFilter.SortDirection}] :: sort direction (optional)
     * @return response [{@link SearchProductResponse}] :: the list of matching authorities
     */
    @Operation(summary = "Searches for products using the provided filter criteria.")
    @GetMapping
    ResponseEntity<SearchProductResponse> search(@RequestParam(defaultValue = "1") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = DEFAULT_SORT_COLUMN) String sortBy,
                                                 @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) SortDirection sortDirection);
}
