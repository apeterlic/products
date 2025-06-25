package dev.beenary.persistence.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyDb, Long> {

    /**
     * Checks whether currency by requested value exists.
     *
     * @param value [{@link String}] :: requested value.
     * @return result [{@link Optional &lt; CurrencyDb &gt; }] :: optional currency.
     */
    boolean existsByValue(final String value);
}
