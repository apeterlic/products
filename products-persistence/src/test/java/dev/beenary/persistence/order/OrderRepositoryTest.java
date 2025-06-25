package dev.beenary.persistence.order;

import dev.beenary.persistence.PostgresTestContainer;
import dev.beenary.persistence.TestDataGenerator;
import dev.beenary.persistence.config.PersistenceConfig;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = PersistenceConfig.class)
@ComponentScan(basePackages = "dev.beenary.persistence")
class OrderRepositoryTest extends PostgresTestContainer {

    private final LocalDateTime from = LocalDateTime.of(2025, 6, 24, 10, 0);

    private final LocalDateTime to = LocalDateTime.now().plusDays(1);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private OrderDb order;

    @BeforeEach
    void setup() {
        final ProductDb product = TestDataGenerator.productDb();
        product.setId(productRepository.save(product).getId());

        orderRepository.deleteAll();

        final OrderItemDb orderItem = TestDataGenerator.orderItem(product);

        order = TestDataGenerator.order(List.of(orderItem));
        orderItem.setOrder(order);

        order.setId(orderRepository.save(order).getId());
    }

    @Test
    void whenFindByCreatedAtBetween_thenReturnsOrders() {
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        final Page<OrderDb> page = orderRepository.findByCreatedAtBetween(from, to, pageable);

        assertEquals(1, page.getTotalElements());
        assertTrue(page.hasContent());
        assertTrue(page.getContent().contains(order));
    }

    @Test
    void givenNoOrdersInRange_whenFindByCreatedAtBetween_thenReturnEmptyPage() {
        final LocalDateTime from = LocalDateTime.now().plusMonths(1);
        final LocalDateTime to = LocalDateTime.now().plusMonths(2);
        final Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        final Page<OrderDb> page = orderRepository.findByCreatedAtBetween(from, to, pageable);

        assertEquals(0, page.getTotalElements());
        assertFalse(page.hasContent());
    }
}
