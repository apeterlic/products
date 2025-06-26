package dev.beenary.persistence.order;

import dev.beenary.api.order.create.OrderItem;
import dev.beenary.api.order.read.OrderItemDetail;
import dev.beenary.common.exception.EntityNotFoundException;
import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.FieldName;
import dev.beenary.persistence.Tables;
import dev.beenary.persistence.product.ProductDb;
import dev.beenary.persistence.product.ProductRepository;
import dev.beenary.persistence.utility.ApiMapper;
import dev.beenary.persistence.utility.EntityMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents order item DB entity.
 */
@Entity
@Table(name = Tables.ORDER_ITEM)
public class OrderItemDb extends BaseEntity<OrderItemDb> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnName.ORDER_ID, nullable = false)
    private OrderDb order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ColumnName.PRODUCT_ID, nullable = false)
    private ProductDb product;

    @Column(name = ColumnName.QUANTITY, nullable = false)
    private Integer quantity;

    /**
     * Instantiates new DB order item.
     */
    public OrderItemDb() {
        super();
    }

    public OrderDb getOrder() {
        return order;
    }

    public void setOrder(final OrderDb order) {
        this.order = order;
    }

    public ProductDb getProduct() {
        return product;
    }

    public void setProduct(final ProductDb product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object) {
        if (object == this) {
            return true;
        }

        if (object == null) {
            return false;
        }

        if (!object.getClass().equals(this.getClass())) {
            return false;
        }

        return ((OrderItemDb) object).getId().equals(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendFields(final StringBuilder sb) {
        sb.append(FieldName.asAppendable(FieldName.QUANTITY)).append(getQuantity());
        sb.append(FieldName.asAppendable(FieldName.PRODUCT_ID)).append(getProduct().getId());
    }
}
