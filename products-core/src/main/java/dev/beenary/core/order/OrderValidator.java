package dev.beenary.core.order;

import dev.beenary.api.order.create.CreateOrderRequest;
import dev.beenary.api.order.read.GetOrderRequest;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.common.utility.Defense;
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
    public static final String ERROR_PRODUCT_NOT_FOUND = "Product with id %s not found or is out " +
            "of stock";

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

    /**
     * Checks whether the product with requested ID exists and if there is enough quantity.
     *
     * @param id       [{@link UUID}] :: product ID.
     * @param quantity [{@link Integer}] :: product quantity.
     */
    private void validateExistenceAndStock(final UUID id, final Integer quantity) {
        final Optional<ProductDb> product =
                productRepository.findByIdAndEnabledTrueAndDeletedFalseAndStockQuantityGreaterThanEqual(id, quantity);
        if (product.isEmpty()) {
            throw new EntityNotFoundException(String.format(ERROR_PRODUCT_NOT_FOUND, id), id.toString());
        }
    }

    /**
     * Checks whether order with a requested ID exists.
     *
     * @param id [{@link UUID}] :: order ID.
     */
    private void validateExistence(final UUID id) {
        final boolean exists = orderRepository.existsById(id);
        if (!exists) {
            throw new EntityNotFoundException(String.format(ERROR_ORDER_NOT_FOUND, id), id.toString());
        }
    }

}
