package dev.beenary.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "dev.beenary.persistence")
@EntityScan(basePackages = {
        "dev.beenary.persistence",
        "dev.beenary.persistence",
        "dev.beenary.persistence",
})
public class PersistenceConfig {

}
