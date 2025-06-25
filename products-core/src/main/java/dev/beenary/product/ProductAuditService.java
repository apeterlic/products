package dev.beenary.product;

import dev.beenary.persistence.product.ProductDb;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a service class for managing operations on product history table.
 *
 * @author anapeterlic
 */
public interface ProductAuditService {

    ProductDb getProductAtRevision(final UUID productId, final LocalDateTime updatedAt);
}
