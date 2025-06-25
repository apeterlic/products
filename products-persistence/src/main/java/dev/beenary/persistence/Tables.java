package dev.beenary.persistence;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

/**
 * Contains table name constants.
 */
public final class Tables {

    public static final String PRODUCT = "product";
    public static final String PRODUCT_AUDIT = "product_aud";
    public static final String ORDER = "orders";
    public static final String ORDER_ITEM = "order_item";
    public static final String CATEGORY = "category";
    public static final String CURRENCY = "currency";

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private Tables() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }
}
