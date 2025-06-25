package dev.beenary.core.product;

import dev.beenary.api.product.read.GetProductRequest;
import dev.beenary.api.product.read.GetProductResponse;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductValidator productValidator;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void givenGetProductRequest_whenGet_thenReturnGetProductResponse() {
        final GetProductRequest request = new GetProductRequest();
        request.setId(UUID.randomUUID());

        when(productValidator.validate(request)).thenReturn(request.getId());
        when(productRepository.getReferenceById(request.getId())).thenReturn(new ProductDb());

        final GetProductResponse response = productService.get(request);
        assertNotNull(response);
    }

}