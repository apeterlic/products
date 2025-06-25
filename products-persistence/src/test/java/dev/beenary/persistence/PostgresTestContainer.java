package dev.beenary.persistence;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class PostgresTestContainer {

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", PostgresContainerConfig::getJdbcUrl);
        registry.add("spring.datasource.username", PostgresContainerConfig::getUsername);
        registry.add("spring.datasource.password", PostgresContainerConfig::getPassword);
    }

}
