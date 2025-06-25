package dev.beenary.core.order;

import dev.beenary.api.order.create.CreateOrderRequest;
import dev.beenary.api.order.read.GetOrderRequest;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.persistence.order.OrderRepository;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static dev.beenary.core.DataGenerator.fromJson;
import static dev.beenary.core.order.OrderValidator.ERROR_ORDER_NOT_FOUND;
import static dev.beenary.core.order.OrderValidator.ERROR_PRODUCT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderValidatorTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderValidator orderValidator;

    @Test
    void givenGetOrderRequest_whenValidate_thenReturnOk() {
        final UUID id = UUID.randomUUID();
        when(orderRepository.existsById(any())).thenReturn(true);
        final GetOrderRequest request = new GetOrderRequest();
        request.setId(id);

        final UUID result = orderValidator.validate(request);
        assertNotNull(result);
        assertEquals(id, result);
    }

    @Test
    void givenGetOrderRequestNoExistingOrder_whenValidate_thenThrowException() {
        final UUID id = UUID.randomUUID();
        when(orderRepository.existsById(any())).thenReturn(false);
        final GetOrderRequest request = new GetOrderRequest();
        request.setId(id);

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> orderValidator.validate(request));

        assertNotNull(exception);
        assertEquals(String.format(ERROR_ORDER_NOT_FOUND, id), exception.getMessage());
    }

    @Test
    void givenCreateOrderRequest_whenValidate_thenReturnOk() throws IOException {
        final ProductDb productDb = new ProductDb();
        productDb.setStockQuantity(3);

        final CreateOrderRequest request = fromJson(CreateOrderRequest.class, "order-create.json");
        when(productRepository.findByIdAndEnabledTrueAndDeletedFalseAndStockQuantityGreaterThanEqual(request.getPayload().getOrderItems().getFirst().getProductId()
                , request.getPayload().getOrderItems().getFirst().getQuantity())).thenReturn(Optional.of(productDb));
        when(productRepository.findById(any())).thenReturn(Optional.of(productDb));

        assertDoesNotThrow(() -> orderValidator.validate(request));
    }

    @Test
    void givenCreateOrderRequestNotExistingProduct_whenValidate_thenReturnThrowException() throws IOException {
        when(productRepository.findByIdAndEnabledTrueAndDeletedFalseAndStockQuantityGreaterThanEqual(any(), any())).thenReturn(Optional.empty());

        final CreateOrderRequest request = fromJson(CreateOrderRequest.class, "order-create.json");

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> orderValidator.validate(request));

        assertNotNull(exception);
        assertEquals(String.format(ERROR_PRODUCT_NOT_FOUND,
                        request.getPayload().getOrderItems().getFirst().getProductId()),
                exception.getMessage());
    }

    @Test
    void givenCreateOrderRequestNoProductOnStock_whenValidate_thenReturnThrowException() throws IOException {
        final CreateOrderRequest request = fromJson(CreateOrderRequest.class, "order-create.json");

        when(productRepository.findByIdAndEnabledTrueAndDeletedFalseAndStockQuantityGreaterThanEqual(any(), any())).thenReturn(Optional.empty());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> orderValidator.validate(request));

        assertNotNull(exception);
        assertEquals(String.format(ERROR_PRODUCT_NOT_FOUND, request.getPayload().getOrderItems().getFirst().getProductId()),
                exception.getMessage());
    }

}