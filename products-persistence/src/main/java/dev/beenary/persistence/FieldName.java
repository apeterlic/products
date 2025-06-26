package dev.beenary.persistence;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

public final class FieldName {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String CODE = "code";
    public static final String PRICE = "price";
    public static final String STOCK_QUANTITY = "stockQuantity";
    public static final String ENABLED = "enabled";
    public static final String DELETED = "deleted";
    public static final String EMAIL = "customerEmail";
    public static final String CREATED_AT = "createdAt";
    public static final String UPDATED_AT = "updatedAt";
    public static final String PRODUCT_ID = "productId";
    public static final String QUANTITY = "quantity";

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private FieldName() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }

    /**
     * Converts the {@code field} to an appender compatible {@link String} representation.
     *
     * @return appender value [String] :: the appender value
     */
    public static String asAppendable(final String field) {
        return String.format(", %s=", field);
    }
}
