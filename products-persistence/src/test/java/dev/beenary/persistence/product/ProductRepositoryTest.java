package dev.beenary.persistence.product;

import dev.beenary.persistence.PostgresTestContainer;
import dev.beenary.persistence.config.PersistenceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = PersistenceConfig.class)
@ComponentScan(basePackages = "dev.beenary.persistence")
class ProductRepositoryTest extends PostgresTestContainer {

    @Autowired
    private ProductRepository productRepository;

    private ProductDb product;

    @BeforeEach
    void setUp() {
        product = new ProductDb();
        product.setCode("D001");
        product.setName("Bonsai tree");
        product.setDescription("Beautiful bonsai tree for every living room.");
        product.setPrice(BigDecimal.valueOf(30));
        product.setVat(25);
        product.setCurrency("EUR");
        product.setCategory("PHYSICAL");
        product.setEnabled(true);
        product.setStockQuantity(10);
        product.setId(productRepository.save(product).getId());
    }

    @Test
    void givenId_whenFindByIdAndEnabledTrueAndDeletedFalse_thenOk() {
        final Optional<ProductDb> result =
                productRepository.findByIdAndEnabledTrueAndDeletedFalse(product.getId());

        assertTrue(result.isPresent());
        assertEquals(product.getId(), result.get().getId());
    }

    @Test
    void givenId_whenSoftDelete_thenOk() {
        productRepository.softDelete(product.getId());

        final Optional<ProductDb> result =
                productRepository.findByIdAndEnabledTrueAndDeletedFalse(product.getId());
        assertFalse(result.isPresent());

        final Optional<ProductDb> stillExists = productRepository.findById(product.getId());
        assertTrue(stillExists.isPresent());
        assertEquals(product.getId(), stillExists.get().getId());
    }

    @Test
    void givenCode_whenFindByCodeAndEnabledTrueAndDeletedFalse_thenOk() {
        final Optional<ProductDb> result =
                productRepository.findByCodeAndEnabledTrueAndDeletedFalse("D001");

        assertTrue(result.isPresent());
    }

    @Test
    void givenProductIds_whenFindByIdIn_thenOk() {
        final Set<ProductDb> products = productRepository.findByIdIn(Set.of(product.getId()));

        assertEquals(1, products.size());
    }

    @Test
    void givenQuantity_whenFindByIdAndEnabledTrueAndDeletedFalseAndStockQuantityGreaterThanEqual_thenOk() {
        final Integer quantity = 5;

        final Optional<ProductDb> result =
                productRepository.findByIdAndEnabledTrueAndDeletedFalseAndStockQuantityGreaterThanEqual(product.getId(), quantity);

        assertTrue(result.isPresent());
    }

    @Test
    void givenExceededQuantity_whenFindByIdAndEnabledTrueAndDeletedFalseAndStockQuantityGreaterThanEqual_thenNotFound() {
        final Integer quantity = 15;

        final Optional<ProductDb> result =
                productRepository.findByIdAndEnabledTrueAndDeletedFalseAndStockQuantityGreaterThanEqual(product.getId(), quantity);

        assertTrue(result.isEmpty());
    }

    @Test
    void givenId_whenFindByIdAndDeletedFalse_thenReturnProduct() {
        final Optional<ProductDb> result =
                productRepository.findByIdAndDeletedFalse(product.getId());

        assertTrue(result.isPresent());
    }
}
