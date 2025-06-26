package dev.beenary.core.currency;

import dev.beenary.api.Resource;
import dev.beenary.core.utility.ApiMapper;
import dev.beenary.persistence.currency.CurrencyDb;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

public final class CurrencyMapper {

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private CurrencyMapper() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }

    public static ApiMapper<CurrencyDb, Resource> apiMapper() {
        return entity -> {
            final Resource product = new Resource();
            product.setName(entity.getValue());
            return product;
        };
    }

}
