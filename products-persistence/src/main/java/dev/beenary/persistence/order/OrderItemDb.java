package dev.beenary.persistence.order;

import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.Table;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.api.order.create.OrderItem;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import dev.beenary.common.utility.EntityMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents order item DB entity.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = Table.ORDER_ITEM)
public class OrderItemDb extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnName.ORDER_ID, nullable = false)
    private OrderDb order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnName.PRODUCT_ID, nullable = false)
    private ProductDb product;

    @Column(name = ColumnName.QUANTITY, nullable = false)
    private Integer quantity;

    public static EntityMapper<OrderItemDb, OrderItem> entityMapper(final ProductRepository productRepository, final OrderDb order) {
        return dto -> {
            final OrderItemDb orderItem = new OrderItemDb();
            final ProductDb product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + dto.getProductId()));
            product.setStockQuantity(product.getStockQuantity() - dto.getQuantity());
            orderItem.setProduct(product);
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            return orderItem;
        };
    }
}
