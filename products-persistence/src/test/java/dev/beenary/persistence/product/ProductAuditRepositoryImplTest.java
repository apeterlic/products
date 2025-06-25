package dev.beenary.persistence.product;

import dev.beenary.persistence.PostgresTestContainer;
import dev.beenary.persistence.config.PersistenceConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TestTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = PersistenceConfig.class)
@ComponentScan(basePackages = "dev.beenary.persistence")
class ProductAuditRepositoryImplTest extends PostgresTestContainer {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAuditRepository productAuditRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private ProductDb product;

    @Commit
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
    void givenSavedProduct_whenGetProductsAtRevision_thenReturnRevision() {
        TestTransaction.flagForCommit();
        TestTransaction.end();

        TestTransaction.start();

        final List<ProductDb> result =
                productAuditRepository.getProductsAtRevision(Set.of(product.getId()),
                        LocalDateTime.now());

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(product.getId(), result.getFirst().getId());
    }

    @Test
    void givenUpdatedProduct_whenGetProductsAtRevision_thenReturnLastRevisions() {
        product.setPrice(BigDecimal.valueOf(30.00));
        productRepository.save(product);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        TestTransaction.start();

        final List<ProductDb> result =
                productAuditRepository.getProductsAtRevision(Set.of(product.getId()),
                        LocalDateTime.now());

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(product.getId(), result.getFirst().getId());
        assertEquals(BigDecimal.valueOf(30.00).setScale(2), result.getFirst().getPrice());
    }
}