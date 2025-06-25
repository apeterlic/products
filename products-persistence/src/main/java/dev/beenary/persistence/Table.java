package dev.beenary.persistence;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

/**
 * Contains table name constants.
 */
public final class Table {

    public static final String PRODUCT = "PRODUCT";
    public static final String ORDER = "ORDERS";
    public static final String ORDER_ITEM = "ORDER_ITEM";
    public static final String CATEGORY = "CATEGORY";
    public static final String CURRENCY = "CURRENCY";

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private Table() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }
}
