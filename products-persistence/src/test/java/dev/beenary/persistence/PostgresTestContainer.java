package dev.beenary.persistence;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Configuration for the test PostgreSQL container.
 * Contains static members so container can be shared between different test classes.
 */
@Testcontainers
public class PostgresTestContainer {

    public static final PostgreSQLContainer<?> CONTAINER;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", PostgresTestContainer::getJdbcUrl);
        registry.add("spring.datasource.username", PostgresTestContainer::getUsername);
        registry.add("spring.datasource.password", PostgresTestContainer::getPassword);
    }

    static {
        CONTAINER = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("products-test")
                .withUsername("beenary")
                .withPassword("beenary");
        CONTAINER.start();
    }

    public static String getJdbcUrl() {
        return CONTAINER.getJdbcUrl();
    }

    public static String getUsername() {
        return CONTAINER.getUsername();
    }

    public static String getPassword() {
        return CONTAINER.getPassword();
    }

}
