package dev.beenary.persistence.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDb, Long> {

    /**
     * Finds category by requested value.
     *
     * @param value [{@link String}] :: requested value.
     * @return result [{@link Optional &lt; CategoryDb &gt; }] :: optional category.
     */
    Optional<CategoryDb> findByValue(final String value);
}
