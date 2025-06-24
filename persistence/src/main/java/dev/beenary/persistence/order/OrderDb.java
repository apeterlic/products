package dev.beenary.persistence.order;

import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.Table;
import dev.beenary.order.create.Order;
import dev.beenary.order.read.OrderDetails;
import dev.beenary.order.read.OrderItemDetails;
import dev.beenary.persistence.product.ProductRepository;
import dev.beenary.common.utility.ApiMapper;
import dev.beenary.common.utility.EntityMapper;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = Table.ORDER)
public class OrderDb extends BaseEntity {

    @Column(name = ColumnName.EMAIL, nullable = false)
    private String customerEmail;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemDb> orderItems;

    public static EntityMapper<OrderDb, Order> entityMapper(final ProductRepository productRepository) {
        return dto -> {
            final OrderDb order = new OrderDb();
            order.setCustomerEmail(dto.getEmail());
            order.setOrderItems(OrderItemDb.entityMapper(productRepository, order)
                    .toUnmodifieableEntityList(dto.getOrderItems()));
            return order;
        };
    }

    public static ApiMapper<OrderDetails, OrderDb> apiMapper() {
        return entity -> {
            final List<OrderItemDetails> items = entity.getOrderItems()
                    .stream()
                    .map(item -> {
                        final OrderItemDetails orderItemDetailsDto = new OrderItemDetails();
                        orderItemDetailsDto.setCode(item.getProduct().getCode());
                        orderItemDetailsDto.setName(item.getProduct().getName());
                        orderItemDetailsDto.setDescription(item.getProduct().getDescription());
                        orderItemDetailsDto.setCurrency(item.getProduct().getCurrency());
                        orderItemDetailsDto.setPrice(item.getProduct().getPrice());
                        orderItemDetailsDto.setVat(item.getProduct().getVat());
                        orderItemDetailsDto.setTotalPrice(item.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity())));
                        orderItemDetailsDto.setQuantity(item.getQuantity());
                        return orderItemDetailsDto;
                    })
                    .toList();

            final BigDecimal totalPrice = BigDecimal.valueOf(items.parallelStream()
                    .mapToDouble(item -> item.getTotalPrice()
                            .doubleValue())
                    .sum());

            final OrderDetails orderItem = new OrderDetails();
            orderItem.setEmail(entity.getCustomerEmail());
            orderItem.setOrderItems(items);
            orderItem.setCreatedAt(entity.getCreatedAt());
            orderItem.setTotalPrice(totalPrice);
            return orderItem;
        };
    }
}
