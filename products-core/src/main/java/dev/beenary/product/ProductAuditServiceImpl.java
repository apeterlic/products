package dev.beenary.product;

import dev.beenary.persistence.product.ProductDb;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ProductAuditServiceImpl implements ProductAuditService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ProductDb getProductAtRevision(final UUID productId, final LocalDateTime updatedAt) {
        final AuditReader reader = AuditReaderFactory.get(entityManager);
        final Number revision = reader.getRevisionNumberForDate(updatedAt);
        return (ProductDb) reader.createQuery().forEntitiesAtRevision(ProductDb.class, revision).add(AuditEntity.id().eq(productId)).getSingleResult();
    }
}
