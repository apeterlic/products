package dev.beenary.order;

import dev.beenary.PaginationFilter;
import dev.beenary.SortingFilter;
import dev.beenary.TimeFilter;
import dev.beenary.common.utility.Defense;
import dev.beenary.common.utility.SortDirection;
import dev.beenary.order.create.CreateOrderRequest;
import dev.beenary.order.create.CreateOrderResponse;
import dev.beenary.order.read.GetOrderRequest;
import dev.beenary.order.read.GetOrderResponse;
import dev.beenary.order.read.SearchOrderRequest;
import dev.beenary.order.read.SearchOrderResponse;
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

    // FIXME - group by product Id and check quantity
    public ResponseEntity<CreateOrderResponse> create(final CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.create(request));
    }

    @Override
    public ResponseEntity<SearchOrderResponse> search(final int page,
                                                      final int size,
                                                      final String sortBy,
                                                      final SortDirection sortDirection,
                                                      final LocalDateTime createdAt) {
        final SearchOrderRequest request = new SearchOrderRequest();
        request.setPaginationFilter(new PaginationFilter(size, page));
        request.setSortingFilter(new SortingFilter(sortBy, sortDirection));
        request.setCreatedFilter(new TimeFilter(createdAt));
        return ResponseEntity.ok(orderService.search(request));
    }
}
