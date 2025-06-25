package dev.beenary.persistence.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDb, Long> {

    /**
     * Checks whether category by requested value exists.
     *
     * @param value [{@link String}] :: requested value.
     * @return result [{@link Optional &lt; CategoryDb &gt; }] :: optional category.
     */
    boolean existsByValue(final String value);
}
