package dev.beenary.core.order;

import dev.beenary.api.order.create.OrderItem;
import dev.beenary.api.order.read.OrderItemDetail;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.core.utility.ApiMapper;
import dev.beenary.core.utility.EntityMapper;
import dev.beenary.persistence.order.OrderDb;
import dev.beenary.persistence.order.OrderItemDb;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;

import java.math.BigDecimal;

import static dev.beenary.common.utility.Constant.ERROR_CLASS_INSTANTIATION;

public final class OrderItemMapper {

    /**
     * Class contains only static members; thus it cannot be instantiated.
     */
    private OrderItemMapper() {
        throw new UnsupportedOperationException(ERROR_CLASS_INSTANTIATION);
    }

    public static EntityMapper<OrderItem, OrderItemDb> entityMapper(final ProductRepository productRepository, final OrderDb order) {
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

    public static ApiMapper<OrderItemDb, OrderItemDetail> apiMapper() {
        return entity -> {
            final OrderItemDetail orderItemDetail = new OrderItemDetail();
            orderItemDetail.setId(entity.getId());
            orderItemDetail.setCode(entity.getProduct().getCode());
            orderItemDetail.setName(entity.getProduct().getName());
            orderItemDetail.setDescription(entity.getProduct().getDescription());
            orderItemDetail.setCurrency(entity.getProduct().getCurrency());
            orderItemDetail.setCategory(entity.getProduct().getCategory());
            orderItemDetail.setPrice(entity.getProduct().getPrice());
            orderItemDetail.setVat(entity.getProduct().getVat());
            orderItemDetail.setTotalPrice(entity.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(entity.getQuantity())));
            orderItemDetail.setQuantity(entity.getQuantity());
            return orderItemDetail;
        };
    }

}
