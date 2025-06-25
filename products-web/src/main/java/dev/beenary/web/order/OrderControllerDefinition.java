package dev.beenary.web.order;

import dev.beenary.api.SortingFilter;
import dev.beenary.api.order.create.CreateOrderRequest;
import dev.beenary.api.order.create.CreateOrderResponse;
import dev.beenary.api.order.read.GetOrderResponse;
import dev.beenary.api.order.read.SearchOrderResponse;
import dev.beenary.common.utility.SortDirection;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;

import static dev.beenary.api.SortingFilter.DEFAULT_SORT_COLUMN;
import static dev.beenary.api.SortingFilter.DEFAULT_SORT_DIRECTION;

/**
 * Provides REST API endpoints for managing orders.
 *
 * @author anapeterlic
 * @since 1.0
 */
@RequestMapping("/v1/orders")
public interface OrderControllerDefinition {

    /**
     * Retrieves order details by ID.
     *
     * @param id [{@link UUID}] :: the order ID.
     * @return response [{@link GetOrderResponse}] :: the order details.
     * @throws dev.beenary.common.exception.BusinessException in case of failure.
     */
    @Operation(summary = "Gets order based on the provided ID.")
    @GetMapping("/{id}")
    ResponseEntity<GetOrderResponse> get(@PathVariable("id") final UUID id);

    // FIXME - group by product Id and check quantity

    /**
     * Creates new order.
     *
     * @param request [{@link CreateOrderRequest}] :: the order creation request
     * @return response [{@link CreateOrderResponse}] :: the created order
     */
    @Operation(summary = "Creates order based on the provided request.")
    @PostMapping
    ResponseEntity<CreateOrderResponse> create(@RequestBody @Valid final CreateOrderRequest request);

    /**
     * Searches for orders based on various criteria.
     *
     * @param size          [{@link Integer}] :: number of entities per page (optional)
     * @param page          [{@link Integer}] :: page number (optional)
     * @param sortBy        [{@link String}] :: sort column (optional)
     * @param sortDirection [{@link SortingFilter}] :: sort direction
     *                      (optional)
     * @param from          [{@link LocalDateTime}] :: time period when order was created
     * @param to            [{@link LocalDateTime}] :: time period when order was created
     * @return response [{@link SearchOrderResponse}] :: the list of matching orders
     */
    @Operation(summary = "Searches for orders using the provided filter criteria.")
    @GetMapping
    ResponseEntity<SearchOrderResponse> search(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = DEFAULT_SORT_COLUMN) String sortBy,
                                               @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) SortDirection sortDirection,
                                               @RequestParam LocalDateTime from,
                                               @RequestParam LocalDateTime to);
}
