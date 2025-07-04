package dev.beenary.core.product;

import dev.beenary.api.product.Product;
import dev.beenary.api.product.create.CreateProductRequest;
import dev.beenary.api.product.read.GetProductRequest;
import dev.beenary.api.product.read.GetProductResponse;
import dev.beenary.api.product.update.UpdateProductRequest;
import dev.beenary.common.exception.BusinessException;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static dev.beenary.core.product.ProductValidator.ERROR_DUPLICATE_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductValidator productValidator;

    @InjectMocks
    private InternalProductService productService;

    @Test
    void givenGetProductRequest_whenGet_thenReturnGetProductResponse() {
        final GetProductRequest request = new GetProductRequest();
        request.setId(UUID.randomUUID());

        when(productValidator.validate(request)).thenReturn(request.getId());
        when(productRepository.getReferenceById(request.getId())).thenReturn(new ProductDb());

        final GetProductResponse response = productService.get(request);
        assertNotNull(response);
    }

    @Test
    void givenCreateProductRequestAlreadyExistingCode_whenCreate_thenThrowException() {
        final CreateProductRequest request = new CreateProductRequest();
        request.setPayload(new Product());
        request.getPayload().setCode("TEST");

        final String error = String.format(ERROR_DUPLICATE_CODE, request.getPayload().getCode());
        when(productValidator.validate(request)).thenThrow(new BusinessException(error));

        BusinessException exception = assertThrows(BusinessException.class, () -> productService.create(request));

        assertNotNull(exception);
        assertEquals(error, exception.getMessage());
        verify(productRepository, never()).save(any());
    }

    @Test
    void givenUpdateProductRequestAlreadyExistingCode_whenUpdate_thenThrowException() {
        final UpdateProductRequest request = new UpdateProductRequest();
        request.setPayload(new Product());
        request.getPayload().setCode("TEST");

        final String error = String.format(ERROR_DUPLICATE_CODE, request.getPayload().getCode());
        when(productValidator.validate(request)).thenThrow(new BusinessException(error));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> productService.update(request));

        assertNotNull(exception);
        assertEquals(error, exception.getMessage());
        verify(productRepository, never()).save(any());
    }

}