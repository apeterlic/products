package dev.beenary.persistence.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyDb, Long> {

    Optional<CurrencyDb> findByValue(final String value);
}
