package dev.beenary.persistence;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

/**
 * Represent column name constants.
 */
public final class ColumnName {

    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String CODE = "CODE";
    public static final String PRICE = "PRICE";
    public static final String VAT = "VAT";
    public static final String CURRENCY = "CURRENCY";
    public static final String CATEGORY = "CATEGORY";
    public static final String STOCK_QUANTITY = "STOCK_QUANTITY";
    public static final String ENABLED = "ENABLED";
    public static final String DELETED = "DELETED";
    public static final String EMAIL = "EMAIL";
    public static final String CREATED_AT = "CREATED_AT";
    public static final String UPDATED_AT = "UPDATED_AT";
    public static final String PRODUCT_ID = "PRODUCT_ID";
    public static final String ORDER_ID = "ORDER_ID";
    public static final String VERSION = "VERSION";
    public static final String QUANTITY = "QUANTITY";

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private ColumnName() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }

}
