package dev.beenary.core.currency;

import dev.beenary.api.currency.read.GetCurrencyResponse;
import dev.beenary.common.utility.Defense;
import dev.beenary.persistence.currency.CurrencyRepository;
import org.springframework.stereotype.Service;


@Service
public class InternalCurrencyService implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public InternalCurrencyService(final CurrencyRepository currencyRepository) {
        this.currencyRepository = Defense.notNull(currencyRepository, CurrencyRepository.class.getSimpleName());
    }

    @Override
    public GetCurrencyResponse findAll() {
        return new GetCurrencyResponse(CurrencyMapper.apiMapper().toUnmodifieableDtoList(currencyRepository.findAll()));
    }
}
