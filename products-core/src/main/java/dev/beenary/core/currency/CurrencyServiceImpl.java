package dev.beenary.core.currency;

import dev.beenary.api.currency.GetCurrencyResponse;
import dev.beenary.common.utility.Defense;
import dev.beenary.persistence.currency.CurrencyDb;
import dev.beenary.persistence.currency.CurrencyRepository;
import org.springframework.stereotype.Service;


@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(final CurrencyRepository currencyRepository) {
        this.currencyRepository = Defense.notNull(currencyRepository, CurrencyRepository.class.getSimpleName());
    }

    @Override
    public GetCurrencyResponse findAll() {
        return new GetCurrencyResponse(CurrencyDb.apiMapper().toUnmodifieableDtoList(currencyRepository.findAll()));
    }
}
