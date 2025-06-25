package dev.beenary.persistence;

import dev.beenary.persistence.order.OrderDb;
import dev.beenary.persistence.order.OrderItemDb;
import dev.beenary.persistence.product.ProductDb;

import java.math.BigDecimal;
import java.util.List;

public class TestDataGenerator {

    public static ProductDb product() {
        final ProductDb product = new ProductDb();
        product.setCode("D001");
        product.setName("Bonsai tree");
        product.setDescription("Beautiful bonsai tree for every living room.");
        product.setPrice(BigDecimal.valueOf(30));
        product.setVat(25);
        product.setCurrency("EUR");
        product.setCategory("PHYSICAL");
        product.setEnabled(true);
        product.setStockQuantity(10);
        return product;
    }

    public static OrderItemDb orderItem(final ProductDb productDb) {
        final OrderItemDb orderItem = new OrderItemDb();
        orderItem.setProduct(productDb);
        orderItem.setQuantity(3);
        return orderItem;
    }

    public static OrderDb order(final List<OrderItemDb> orderItem) {
        final OrderDb order = new OrderDb();
        order.setCustomerEmail("test@gmail.com");
        order.setOrderItems(orderItem);
        return order;
    }
}
