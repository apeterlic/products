package dev.beenary.core.product;

import dev.beenary.common.utility.Defense;
import dev.beenary.persistence.product.ProductAuditRepository;
import dev.beenary.persistence.product.ProductDb;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ProductAuditServiceImpl implements ProductAuditService {

    private final ProductAuditRepository productAuditRepository;

    public ProductAuditServiceImpl(final ProductAuditRepository productAuditRepository) {
        this.productAuditRepository = Defense.notNull(productAuditRepository,
                ProductAuditRepository.class.getSimpleName());
    }

    @Override
    public List<ProductDb> getProductsAtRevision(final Set<UUID> productIds, final LocalDateTime orderCreatedAt) {
        return productAuditRepository.getProductsAtRevision(productIds, orderCreatedAt);
    }
}
