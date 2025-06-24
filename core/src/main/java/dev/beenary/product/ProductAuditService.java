package dev.beenary.product;

import dev.beenary.persistence.product.ProductDb;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ProductAuditService {

    ProductDb getProductAtRevision(final UUID productId, final LocalDateTime updatedAt);
}
