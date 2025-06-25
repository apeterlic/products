package dev.beenary.persistence.product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductAuditRepository {

    List<ProductDb> getProductsAtRevision(final Set<UUID> productIds,
                                          final LocalDateTime orderCreatedAt);
}
