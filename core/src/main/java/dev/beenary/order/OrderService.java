package dev.beenary.order;

import dev.beenary.order.create.CreateOrderRequest;
import dev.beenary.order.create.CreateOrderResponse;
import dev.beenary.order.read.GetOrderRequest;
import dev.beenary.order.read.GetOrderResponse;
import dev.beenary.order.read.SearchOrderRequest;
import dev.beenary.order.read.SearchOrderResponse;
import jakarta.transaction.Transactional;

public interface OrderService {

    GetOrderResponse get(final GetOrderRequest request);

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    CreateOrderResponse create(final CreateOrderRequest request);

    SearchOrderResponse search(final SearchOrderRequest request);
}
