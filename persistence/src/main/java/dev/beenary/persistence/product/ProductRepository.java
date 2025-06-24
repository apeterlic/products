package dev.beenary.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductDb, UUID> {

    @Modifying
    @Query("UPDATE PRODUCT p SET p.deleted = true WHERE p.id = :id")
    ProductDb softDelete(@Param("id") UUID id);

    Optional<ProductDb> findByCodeAndDeletedFalse(final String code);

    Optional<ProductDb> findByCodeAndEnabledTrueAndDeletedFalse(final String code);
}
