package dev.beenary.currency;

import dev.beenary.PayloadResponse;
import dev.beenary.Resource;

import java.util.List;

public class GetCurrencyResponse extends PayloadResponse<List<Resource>> {

    public GetCurrencyResponse(final List<Resource> payload) {
        super(payload);
    }
}
