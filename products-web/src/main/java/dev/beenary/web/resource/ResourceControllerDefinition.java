package dev.beenary.web.resource;

import dev.beenary.api.category.read.GetCategoryResponse;
import dev.beenary.api.currency.read.GetCurrencyResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Provides REST API endpoints for managing resources.
 *
 * @author anapeterlic
 * @since 1.0
 */
@RequestMapping("/v1/resources")
public interface ResourceControllerDefinition {

    /**
     * Retrieves all currencies.
     *
     * @return response [{@link GetCurrencyResponse}] :: existing currencies.
     * @throws dev.beenary.common.exception.BusinessException in case of failure.
     */
    @Operation(summary = "Gets all currencies.")
    @GetMapping("/currencies")
    ResponseEntity<GetCurrencyResponse> getCurrencies();

    /**
     * Retrieves all categories.
     *
     * @return response [{@link GetCategoryResponse}] :: existing categories.
     * @throws dev.beenary.common.exception.BusinessException in case of failure.
     */
    @Operation(summary = "Gets all currencies.")
    @GetMapping("/categories")
    ResponseEntity<GetCategoryResponse> getCategories();

}
