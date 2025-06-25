package dev.beenary.persistence.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderDb, UUID> {

    /**
     * Finds orders by requested creation time.
     *
     * @param createdAt [{@link LocalDateTime}] :: order creation time.
     * @param pageable  [{@link Pageable}] :: pagination and sorting criteria.
     * @return result [{@link Page &lt; OrderDb &gt; }] :: paginated orders.
     */

    Page<OrderDb> findByCreatedAt(final LocalDateTime createdAt, final Pageable pageable);

}
