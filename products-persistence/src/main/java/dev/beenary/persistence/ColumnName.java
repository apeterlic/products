package dev.beenary.persistence;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

/**
 * Represent column name constants.
 */
public final class ColumnName {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String CODE = "code";
    public static final String PRICE = "price";
    public static final String VAT = "vat";
    public static final String CURRENCY = "currency";
    public static final String CATEGORY = "category";
    public static final String STOCK_QUANTITY = "stock_quantity";
    public static final String ENABLED = "enabled";
    public static final String DELETED = "deleted";
    public static final String EMAIL = "email";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String PRODUCT_ID = "product_id";
    public static final String ORDER_ID = "order_id";
    public static final String VERSION = "version";
    public static final String QUANTITY = "quantity";

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private ColumnName() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }

}
