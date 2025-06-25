package dev.beenary.persistence;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class PostgresContainerConfig {

    public static final PostgreSQLContainer<?> CONTAINER;

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
