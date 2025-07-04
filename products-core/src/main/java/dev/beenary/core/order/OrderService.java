package dev.beenary.core.order;

import dev.beenary.api.order.create.CreateOrderRequest;
import dev.beenary.api.order.create.CreateOrderResponse;
import dev.beenary.api.order.read.GetOrderRequest;
import dev.beenary.api.order.read.GetOrderResponse;
import dev.beenary.api.order.read.SearchOrderRequest;
import dev.beenary.api.order.read.SearchOrderResponse;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Represents a service class for managing operations on order table.
 *
 * @author anapeterlic
 */
public interface OrderService {

    GetOrderResponse get(final GetOrderRequest request);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    CreateOrderResponse create(final CreateOrderRequest request);

    SearchOrderResponse search(final SearchOrderRequest request);
}
