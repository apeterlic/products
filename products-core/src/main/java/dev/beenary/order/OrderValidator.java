package dev.beenary.order;

import dev.beenary.common.exception.BusinessException;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.common.utility.Defense;
import dev.beenary.api.order.create.CreateOrderRequest;
import dev.beenary.api.order.read.GetOrderRequest;
import dev.beenary.persistence.order.OrderDb;
import dev.beenary.persistence.order.OrderRepository;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Validation class for validating order requests.
 *
 * @author anapeterlic
 * @since 1.0
 */
@Component
public class OrderValidator {

    public static final String ERROR_ORDER_NOT_FOUND = "Order with id %s not found";
    public static final String ERROR_PRODUCT_NOT_FOUND = "Product with id %s not found";
    public static final String ERROR_PRODUCT_QUANTITY_EXCEEDED = "There is not enough product %s in stock.Please reduce the quantity.";

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    public OrderValidator(final ProductRepository productRepository,
                          final OrderRepository orderRepository) {
        this.productRepository = Defense.notNull(productRepository, ProductRepository.class.getSimpleName());
        this.orderRepository = Defense.notNull(orderRepository, OrderRepository.class.getSimpleName());
    }

    public UUID validate(final GetOrderRequest request) {
        validateExistence(request.getId());
        return request.getId();
    }

    public OrderDb validate(final CreateOrderRequest request) {
        request.getPayload()
                .getOrderItems()
                .parallelStream()
                .forEach(orderItem -> validateExistenceAndStock(orderItem.getProductId(), orderItem.getQuantity()));
        return OrderDb.entityMapper(productRepository).toEntity(request.getPayload());
    }

    private void validateExistenceAndStock(final UUID id, final Integer quantity) {
        final Optional<ProductDb> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException(String.format(ERROR_PRODUCT_NOT_FOUND, id));
        }

        if (product.get().getStockQuantity() < quantity) {
            throw new BusinessException(String.format(ERROR_PRODUCT_QUANTITY_EXCEEDED, product.get().getCode()));
        }
    }

    private void validateExistence(final UUID id) {
        final Optional<OrderDb> product = orderRepository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException(String.format(ERROR_ORDER_NOT_FOUND, id));
        }
    }

}
