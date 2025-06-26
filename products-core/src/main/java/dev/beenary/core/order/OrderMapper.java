package dev.beenary.core.order;

import dev.beenary.api.order.create.Order;
import dev.beenary.api.order.read.OrderDetail;
import dev.beenary.api.order.read.OrderItemDetail;
import dev.beenary.core.utility.ApiMapper;
import dev.beenary.core.utility.EntityMapper;
import dev.beenary.persistence.order.OrderDb;
import dev.beenary.persistence.product.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

public final class OrderMapper {

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private OrderMapper() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }

    public static EntityMapper<Order, OrderDb> entityMapper(final ProductRepository productRepository) {
        return dto -> {
            final OrderDb order = new OrderDb();
            order.setCustomerEmail(dto.getEmail());
            order.setOrderItems(OrderItemMapper.entityMapper(productRepository, order)
                    .toUnmodifieableEntityList(dto.getOrderItems()));
            return order;
        };
    }

    public static ApiMapper<OrderDb, OrderDetail> apiMapper() {
        return entity -> {
            final OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(entity.getId());
            orderDetail.setEmail(entity.getCustomerEmail());

            final List<OrderItemDetail> items =
                    OrderItemMapper.apiMapper().toUnmodifieableDtoList(entity.getOrderItems());
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
