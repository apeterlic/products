package dev.beenary.web.order;

import dev.beenary.api.PaginationFilter;
import dev.beenary.api.SortingFilter;
import dev.beenary.api.TimeFilter;
import dev.beenary.api.order.create.CreateOrderRequest;
import dev.beenary.api.order.create.CreateOrderResponse;
import dev.beenary.api.order.read.GetOrderRequest;
import dev.beenary.api.order.read.GetOrderResponse;
import dev.beenary.api.order.read.SearchOrderRequest;
import dev.beenary.api.order.read.SearchOrderResponse;
import dev.beenary.common.utility.Defense;
import dev.beenary.common.utility.SortDirection;
import dev.beenary.core.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Provides REST entry point for order.
 *
 * @author anapeterlic
 * @since 1.0
 */
@RestController
public class OrderController implements OrderControllerDefinition {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = Defense.notNull(orderService, OrderService.class.getSimpleName());
    }

    @Override
    public ResponseEntity<GetOrderResponse> get(final UUID id) {
        final GetOrderRequest request = new GetOrderRequest();
        request.setId(id);
        return ResponseEntity.ok(orderService.get(request));
    }

    public ResponseEntity<CreateOrderResponse> create(final CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.create(request));
    }

    @Override
    public ResponseEntity<SearchOrderResponse> search(final int page,
                                                      final int size,
                                                      final String sortBy,
                                                      final SortDirection sortDirection,
                                                      final LocalDateTime from,
                                                      final LocalDateTime to) {
        final SearchOrderRequest request = new SearchOrderRequest();
        request.setPaginationFilter(new PaginationFilter(page, size));
        request.setSortingFilter(new SortingFilter(sortBy, sortDirection));
        request.setCreatedFilter(new TimeFilter(from, to));
        return ResponseEntity.ok(orderService.search(request));
    }
}
