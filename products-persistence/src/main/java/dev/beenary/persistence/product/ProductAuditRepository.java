package dev.beenary.persistence.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductAuditRepository {

    /**
     * Finds products by requested ID at revision for a given date and time.
     *
     * @param productIds   [{@link Set}&lt;{@link UUID}&gt;] :: the set of product IDs to look up
     * @param revisionDate [{@link LocalDateTime}] :: date and time of the revision
     * @return result [{@link List}&lt;{@link ProductDb}&gt;] :: list of products.
     */
    List<ProductDb> getProductsAtRevision(final Set<UUID> productIds,
                                          final LocalDateTime revisionDate);
}
