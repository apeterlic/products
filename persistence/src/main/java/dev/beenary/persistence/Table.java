package dev.beenary.persistence;

public final class Table {

    public static final String PRODUCT = "PRODUCT";

    public static final String ORDER = "ORDERS";

    public static final String ORDER_ITEM = "ORDER_ITEM";

    public static final String CATEGORY = "CATEGORY";

    public static final String CURRENCY = "CURRENCY";

    private Table() {
        throw new UnsupportedOperationException();
    }
}
