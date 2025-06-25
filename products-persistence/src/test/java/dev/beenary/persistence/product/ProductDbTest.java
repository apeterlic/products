package dev.beenary.persistence.product;

import dev.beenary.api.product.Product;
import dev.beenary.persistence.TestDataGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductDbTest {

    @Test
    void apiMapper() {
        final ProductDb productDb = TestDataGenerator.productDb();
        final Product product = ProductDb.apiMapper().toDto(productDb);

        assertAll(() -> {
            assertNotNull(product);
            assertEquals(productDb.getCode(), product.getCode());
            assertEquals(productDb.getName(), product.getName());
            assertEquals(productDb.getDescription(), product.getDescription());
            assertEquals(productDb.getPrice(), product.getPrice());
            assertEquals(productDb.getCurrency(), product.getCurrency());
            assertEquals(productDb.getCategory(), product.getCategory());
            assertEquals(productDb.getVat(), product.getVat());
            assertEquals(productDb.isEnabled(), product.getEnabled());
            assertEquals(productDb.getStockQuantity(), product.getStockQuantity());

        });
    }

    @Test
    void entityMapper() {
        final Product product = TestDataGenerator.product();
        final ProductDb productDb = ProductDb.entityMapper().toEntity(product);

        assertAll(() -> {
            assertNotNull(productDb);
            assertEquals(product.getCode(), productDb.getCode());
            assertEquals(product.getName(), productDb.getName());
            assertEquals(product.getDescription(), productDb.getDescription());
            assertEquals(product.getPrice(), productDb.getPrice());
            assertEquals(product.getCurrency(), productDb.getCurrency());
            assertEquals(product.getCategory(), productDb.getCategory());
            assertEquals(product.getVat(), productDb.getVat());
            assertEquals(product.getEnabled(), productDb.isEnabled());
            assertEquals(product.getStockQuantity(), productDb.getStockQuantity());

        });
    }
}