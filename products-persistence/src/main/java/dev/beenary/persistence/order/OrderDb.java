package dev.beenary.persistence.order;

import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.FieldName;
import dev.beenary.persistence.Tables;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;

/**
 * Represents order DB entity.
 */
@Entity
@Table(name = Tables.ORDER)
public class OrderDb extends BaseEntity<OrderDb> {

    @Column(name = ColumnName.EMAIL, nullable = false)
    private String customerEmail;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItemDb> orderItems;

    /**
     * Instantiates new DB order.
     */
    public OrderDb() {
        super();
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(final String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<OrderItemDb> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(final List<OrderItemDb> orderItems) {
        this.orderItems = orderItems;
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

        return ((OrderDb) object).getId().equals(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendFields(final StringBuilder sb) {
        sb.append(FieldName.asAppendable(FieldName.EMAIL)).append(getCustomerEmail());
    }
}
