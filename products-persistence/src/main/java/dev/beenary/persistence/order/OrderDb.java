package dev.beenary.persistence.order;

import dev.beenary.api.order.create.Order;
import dev.beenary.api.order.read.OrderDetail;
import dev.beenary.api.order.read.OrderItemDetail;
import dev.beenary.persistence.utility.ApiMapper;
import dev.beenary.persistence.utility.EntityMapper;
import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.Tables;
import dev.beenary.persistence.product.ProductRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents order DB entity.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Tables.ORDER)
public class OrderDb extends BaseEntity {

    @Column(name = ColumnName.EMAIL, nullable = false)
    private String customerEmail;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemDb> orderItems;

    public static EntityMapper<Order, OrderDb> entityMapper(final ProductRepository productRepository) {
        return dto -> {
            final OrderDb order = new OrderDb();
            order.setCustomerEmail(dto.getEmail());
            order.setOrderItems(OrderItemDb.entityMapper(productRepository, order)
                    .toUnmodifieableEntityList(dto.getOrderItems()));
            return order;
        };
    }

    public static ApiMapper<OrderDb, OrderDetail> apiMapper() {
        return entity -> {
            final OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(entity.getId());
            orderDetail.setEmail(entity.getCustomerEmail());

            final List<OrderItemDetail> items = OrderItemDb.apiMapper().toUnmodifieableDtoList(entity.getOrderItems());
            final BigDecimal totalPrice = BigDecimal.valueOf(items.parallelStream()
                    .mapToDouble(item -> item.getTotalPrice()
                            .doubleValue())
                    .sum());

            orderDetail.setOrderItems(items);
            orderDetail.setCreatedAt(entity.getCreatedAt());
            orderDetail.setTotalPrice(totalPrice);
            return orderDetail;
        };
    }
}
