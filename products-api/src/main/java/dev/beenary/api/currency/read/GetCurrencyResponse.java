package dev.beenary.api.currency.read;

import dev.beenary.api.PayloadResponse;
import dev.beenary.api.Resource;

import java.io.Serial;
import java.util.List;

public class GetCurrencyResponse extends PayloadResponse<List<Resource>> {

    @Serial
    private static final long serialVersionUID = 1L;

    public GetCurrencyResponse(final List<Resource> payload) {
        super(payload);
    }
}
