package dev.beenary.persistence.order;

import dev.beenary.api.order.create.OrderItem;
import dev.beenary.api.order.read.OrderItemDetail;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.common.utility.ApiMapper;
import dev.beenary.common.utility.EntityMapper;
import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.Tables;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Represents order item DB entity.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Tables.ORDER_ITEM)
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
                    .orElseThrow(() -> new EntityNotFoundException("Product not found: " + dto.getProductId(), "productId"));
            product.setStockQuantity(product.getStockQuantity() - dto.getQuantity());
            orderItem.setProduct(product);
            orderItem.setQuantity(dto.getQuantity());
            orderItem.setProduct(product);
            orderItem.setOrder(order);
            return orderItem;
        };
    }

    public static ApiMapper<OrderItemDetail, OrderItemDb> apiMapper() {
        return entity -> {
            final OrderItemDetail orderItemDetail = new OrderItemDetail();
            orderItemDetail.setId(entity.getId());
            orderItemDetail.setCode(entity.getProduct().getCode());
            orderItemDetail.setName(entity.getProduct().getName());
            orderItemDetail.setDescription(entity.getProduct().getDescription());
            orderItemDetail.setCurrency(entity.getProduct().getCurrency());
            orderItemDetail.setPrice(entity.getProduct().getPrice());
            orderItemDetail.setVat(entity.getProduct().getVat());
            orderItemDetail.setTotalPrice(entity.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(entity.getQuantity())));
            orderItemDetail.setQuantity(entity.getQuantity());
            return orderItemDetail;
        };
    }
}
