package dev.beenary.persistence.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    @Modifying
    @Query("UPDATE ProductDb p SET p.deleted = true WHERE p.id = :id")
    void softDelete(@Param("id") UUID id);

    /**
     * Retrieves all products matching the given set of product IDs.
     *
     * @param productIds [{@link Set}&lt;{@link UUID}&gt;] :: the set of product IDs to look up
     * @return result [{@link Set}&lt;{@link ProductDb}&gt;] :: the set of matching product entities.
     */
    Set<ProductDb> findByIdIn(final Set<UUID> productIds);

    /**
     * Finds enabled non deleted product by requested ID and a stock quantity greater or equal than
     * requested.
     *
     * @param id       [{@link UUID}] :: product id.
     * @param quantity [{@link Integer}] :: product quantity.
     * @return result [{@link Optional &lt; ProductDb &gt; }] :: optional product.
     */
    Optional<ProductDb> findByIdAndEnabledTrueAndDeletedFalseAndStockQuantityGreaterThanEqual(final UUID id,
                                                                                              final Integer quantity);

    /**
     * Checks whether non deleted product by requested id exist.
     * Used when a product is retrieved or about to be deleted.
     *
     * @param id [{@link UUID}] :: product id.
     * @return result [{@link Boolean}] :: true if exists, false otherwise.
     */
    boolean existsByIdAndDeletedFalse(final UUID id);

    /**
     * Checks whether non deleted product by requested code exist.
     * Used when a product is updated.
     *
     * @param code [{@link String}] :: product code.
     * @param id   [{@link UUID}] :: product id.
     * @return result [{@link Boolean}] :: true if exists, false otherwise.
     */
    boolean existsByCodeAndDeletedFalseAndIdNot(final String code,
                                                final UUID id);

    /**
     * Checks whether non deleted product by requested code exist.
     * Used when a product is created.
     *
     * @param code [{@link String}] :: product code.
     * @return result [{@link Boolean }] :: true if exists, false otherwise.
     */
    boolean existsByCodeAndDeletedFalse(final String code);

    /**
     * Retrieves non deleted products.
     *
     * @param pageable [{@link Pageable}] :: pagination and sorting criteria.
     * @return result [{@link Page &lt; ProductDb &gt; }] :: paginated products.
     */
    Page<ProductDb> findAllByDeletedFalse(final Pageable pageable);
}
