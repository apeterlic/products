package dev.beenary.persistence.category;

import dev.beenary.persistence.PostgresTestContainer;
import dev.beenary.persistence.config.PersistenceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = PersistenceConfig.class)
@ComponentScan(basePackages = "dev.beenary.persistence")
class CategoryRepositoryTest extends PostgresTestContainer {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void givenValue_whenExistsByValue_thenReturnTrue() {
        assertTrue(categoryRepository.existsByValue("PHYSICAL"));
    }
}