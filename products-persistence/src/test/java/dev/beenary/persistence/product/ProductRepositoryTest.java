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
import org.springframework.test.context.transaction.TestTransaction;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
    void givenId_whenExistsByIdAndDeletedFalse_thenReturnTrue() {
        final boolean result =
                productRepository.existsByIdAndDeletedFalse(product.getId());

        assertTrue(result);
    }

    @Test
    void givenId_whenSoftDelete_thenOk() {
        productRepository.softDelete(product.getId());

        TestTransaction.flagForCommit();
        TestTransaction.end();

        TestTransaction.start();

        final Optional<ProductDb> deletedEntity = productRepository.findById(product.getId());

        assertTrue(deletedEntity.isPresent());
        assertEquals(product.getId(), deletedEntity.get().getId());
        assertTrue(deletedEntity.get().isDeleted());
    }

    @Test
    void givenCodeAndExistingId_whenExistsByCodeAndDeletedFalseAndIdNot_thenReturnFalse() {
        final boolean result =
                productRepository.existsByCodeAndDeletedFalseAndIdNot("D001", product.getId());

        assertFalse(result);
    }

    @Test
    void givenCodeAndNonExistingId_whenExistsByCodeAndDeletedFalseAndIdNot_thenReturnTrue() {
        final boolean result =
                productRepository.existsByCodeAndDeletedFalseAndIdNot("D001", UUID.randomUUID());

        assertTrue(result);
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

}
