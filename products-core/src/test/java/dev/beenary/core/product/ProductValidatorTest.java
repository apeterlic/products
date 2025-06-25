package dev.beenary.core.product;

import dev.beenary.api.product.create.CreateProductRequest;
import dev.beenary.api.product.delete.DeleteProductRequest;
import dev.beenary.api.product.read.GetProductRequest;
import dev.beenary.api.product.update.UpdateProductRequest;
import dev.beenary.common.exception.BusinessException;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.persistence.category.CategoryRepository;
import dev.beenary.persistence.currency.CurrencyRepository;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.UUID;

import static dev.beenary.core.DataGenerator.fromJson;
import static dev.beenary.core.product.ProductValidator.ERROR_DUPLICATE_CODE;
import static dev.beenary.core.product.ProductValidator.ERROR_PRODUCT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductValidatorTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductValidator productValidator;

    @Test
    void givenGetProductRequest_whenValidate_thenReturnOk() {
        final GetProductRequest request = new GetProductRequest();
        request.setId(UUID.randomUUID());
        when(productRepository.existsByIdAndDeletedFalse(any())).thenReturn(true);

        final UUID result = productValidator.validate(request);

        assertNotNull(result);
        assertEquals(request.getId(), result);
    }

    @Test
    void givenGetProductRequestNoProduct_whenValidate_thenThrowException() {
        when(productRepository.existsByIdAndDeletedFalse(any())).thenReturn(false);

        final GetProductRequest request = new GetProductRequest();
        request.setId(UUID.randomUUID());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> productValidator.validate(request));

        assertNotNull(exception);
        assertEquals(String.format(ERROR_PRODUCT_NOT_FOUND, request.getId()), exception.getMessage());
    }

    @Test
    void givenCreateProductRequest_whenValidate_thenReturnOk() throws IOException {
        when(currencyRepository.existsByValue(any())).thenReturn(true);
        when(categoryRepository.existsByValue(any())).thenReturn(true);
        when(productRepository.existsByCodeAndDeletedFalse(any())).thenReturn(false);

        final CreateProductRequest request = fromJson(CreateProductRequest.class, "product-create" +
                ".json");

        final ProductDb result = productValidator.validate(request);

        assertNotNull(result);
        assertEquals(request.getPayload().getCode(), result.getCode());
    }


    @Test
    void givenCreateProductRequestDuplicateCode_whenValidate_thenThrowException() throws IOException {
        when(currencyRepository.existsByValue(any())).thenReturn(true);
        when(categoryRepository.existsByValue(any())).thenReturn(true);
        when(productRepository.existsByCodeAndDeletedFalse(any())).thenReturn(true);

        final CreateProductRequest request = fromJson(CreateProductRequest.class, "product-create" +
                ".json");

        final BusinessException exception = assertThrows(BusinessException.class,
                () -> productValidator.validate(request));

        assertNotNull(exception);
        assertEquals(String.format(ERROR_DUPLICATE_CODE, request.getPayload().getCode()),
                exception.getMessage());
    }

    @Test
    void givenUpdateProductRequest_whenValidate_thenReturnOk() throws IOException {
        when(currencyRepository.existsByValue(any())).thenReturn(true);
        when(categoryRepository.existsByValue(any())).thenReturn(true);
        lenient().when(productRepository.existsByIdAndDeletedFalse(any())).thenReturn(true);
        lenient().when(productRepository.existsByCodeAndDeletedFalseAndIdNot(any(), any())).thenReturn(false);

        final UpdateProductRequest request = fromJson(UpdateProductRequest.class, "product-create" +
                ".json");

        final ProductDb result = productValidator.validate(request);

        assertNotNull(result);
        assertEquals(request.getPayload().getCode(), result.getCode());
    }

    @Test
    void givenUpdateProductRequestNoExistingProduct_whenValidate_thenThrowException() throws IOException {
        when(currencyRepository.existsByValue(any())).thenReturn(true);
        when(categoryRepository.existsByValue(any())).thenReturn(true);
        lenient().when(productRepository.existsByIdAndDeletedFalse(any())).thenReturn(false);
        lenient().when(productRepository.existsByCodeAndDeletedFalseAndIdNot(any(), any())).thenReturn(false);

        final UpdateProductRequest request = fromJson(UpdateProductRequest.class, "product-create" +
                ".json");
        request.setId(UUID.randomUUID());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> productValidator.validate(request));

        assertNotNull(exception);
        assertEquals(String.format(ERROR_PRODUCT_NOT_FOUND, request.getId()), exception.getMessage());
    }

    @Test
    void givenUpdateProductRequestNoExistingCode_whenValidate_thenThrowException() throws IOException {
        when(currencyRepository.existsByValue(any())).thenReturn(true);
        when(categoryRepository.existsByValue(any())).thenReturn(true);
        lenient().when(productRepository.existsByIdAndDeletedFalse(any())).thenReturn(true);
        lenient().when(productRepository.existsByCodeAndDeletedFalseAndIdNot(any(), any())).thenReturn(true);

        final UpdateProductRequest request = fromJson(UpdateProductRequest.class, "product-create" +
                ".json");

        final BusinessException exception = assertThrows(BusinessException.class,
                () -> productValidator.validate(request));

        assertNotNull(exception);
        assertEquals(String.format(ERROR_DUPLICATE_CODE, request.getPayload().getCode()),
                exception.getMessage());
    }

    @Test
    void givenDeleteProductRequest_whenValidate_thenReturnOk() {
        final DeleteProductRequest request = new DeleteProductRequest();
        request.setId(UUID.randomUUID());

        final ProductDb productDb = new ProductDb();
        productDb.setStockQuantity(3);
        when(productRepository.existsByIdAndDeletedFalse(any())).thenReturn(true);

        final UUID result = productValidator.validate(request);

        assertNotNull(result);
        assertEquals(request.getId(), result);
    }

    @Test
    void givenDeleteProductRequestNoProduct_whenValidate_thenThrowException() {
        when(productRepository.existsByIdAndDeletedFalse(any())).thenReturn(false);

        final DeleteProductRequest request = new DeleteProductRequest();
        request.setId(UUID.randomUUID());

        final EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> productValidator.validate(request));

        assertNotNull(exception);
        assertEquals(String.format(ERROR_PRODUCT_NOT_FOUND, request.getId()), exception.getMessage());
    }


}