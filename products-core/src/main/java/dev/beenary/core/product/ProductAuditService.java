package dev.beenary.core.product;

import dev.beenary.persistence.product.ProductDb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a service class for managing operations on product history table.
 *
 * @author anapeterlic
 */
public interface ProductAuditService {

    List<ProductDb> getProductsAtRevision(final Set<UUID> productIds,
                                          final LocalDateTime orderCreatedAt);
}
