package dev.beenary.persistence.product;

import dev.beenary.common.utility.Defense;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Repository
public class ProductAuditRepositoryImpl implements ProductAuditRepository {

    private final EntityManager entityManager;

    public ProductAuditRepositoryImpl(final EntityManager entityManager) {
        this.entityManager = Defense.notNull(entityManager, EntityManager.class.getSimpleName());
    }

    /**
     * Gets products form the audit table at specific revision.
     * NOTE: Deleted entities are not included in the search.
     *
     * @param productIds     [{@link Set}&lt;{@link UUID}&gt;] :: the set of product IDs to look up.
     * @param orderCreatedAt [{@link LocalDateTime}] :: date to find the revision number it
     *                       corresponds to.
     * @return result ::  [{@link List}&lt;{@link ProductDb}&gt;] :: list of products at specific
     * revision.
     */
    @Override
    public List<ProductDb> getProductsAtRevision(final Set<UUID> productIds,
                                                 final LocalDateTime orderCreatedAt) {
        final AuditReader reader = AuditReaderFactory.get(entityManager);

        // vertical - find revision for requested time
        final Number revision = reader.getRevisionNumberForDate(orderCreatedAt);
        log.debug("Revision number: {}", revision.intValue());

        // horizontal - find data at revision
        return reader.createQuery()
                .forEntitiesAtRevision(ProductDb.class, revision)
                .add(AuditEntity.id().in(productIds))
                .getResultList();
    }
}
