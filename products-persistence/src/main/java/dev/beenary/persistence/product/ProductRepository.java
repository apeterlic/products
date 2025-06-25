package dev.beenary.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductDb, UUID> {

    /**
     * Performs soft delete on product by requested ID.
     *
     * @param id [{@link UUID}] :: product ID.
     */
    @Modifying
    @Query("UPDATE ProductDb p SET p.deleted = true WHERE p.id = :id")
    void softDelete(@Param("id") UUID id);

    /**
     * Finds enabled and non deleted product by requested code.
     *
     * @param code [{@link String}] :: product code.
     * @return result [{@link Optional &lt; ProductDb &gt; }] :: optional product.
     */
    Optional<ProductDb> findByCodeAndEnabledTrueAndDeletedFalse(final String code);

    /**
     * Finds enabled and non deleted product by requested id.
     *
     * @param id [{@link String}] :: product id.
     * @return result [{@link Optional &lt; ProductDb &gt; }] :: optional product.
     */
    Optional<ProductDb> findByIdAndEnabledTrueAndDeletedFalse(final UUID id);

    /**
     * Retrieves all products matching the given set of product IDs.
     *
     * @param productIds [{@link Set}&lt;{@link UUID}&gt;] :: the set of product IDs to look up
     * @return result [{@link Set}&lt;{@link ProductDb}&gt;] :: the set of matching product entities.
     */
    Set<ProductDb> findByIdIn(final Set<UUID> productIds);

    Optional<ProductDb> findByIdAndEnabledTrueAndDeletedFalseAndStockQuantityGreaterThanEqual(final UUID id,
                                                                                              final Integer quantity);

    Optional<ProductDb> findByIdAndDeletedFalse(final UUID id);
}
