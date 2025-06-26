package dev.beenary.core.currency;

import dev.beenary.api.currency.read.GetCurrencyResponse;

/**
 * Represents a service class for managing operations on currency table.
 *
 * @author anapeterlic
 */
public interface CurrencyService {

    GetCurrencyResponse findAll();
}
