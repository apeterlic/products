package dev.beenary.core.product;

import dev.beenary.common.utility.Defense;
import dev.beenary.persistence.product.ProductAuditRepository;
import dev.beenary.persistence.product.ProductDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class InternalProductAuditService implements ProductAuditService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductAuditRepository productAuditRepository;

    public InternalProductAuditService(final ProductAuditRepository productAuditRepository) {
        this.productAuditRepository = Defense.notNull(productAuditRepository,
                ProductAuditRepository.class.getSimpleName());
    }

    @Override
    public List<ProductDb> getProductsAtRevision(final Set<UUID> productIds, final LocalDateTime orderCreatedAt) {
        logger.debug("getProductsAtRevision() >> productIds {}, orderCreatedAt {}", productIds, orderCreatedAt);
        return productAuditRepository.getProductsAtRevision(productIds, orderCreatedAt);
    }
}
