package dev.beenary.persistence.product;

import dev.beenary.api.product.Product;
import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.FieldName;
import dev.beenary.persistence.Tables;
import dev.beenary.persistence.utility.ApiMapper;
import dev.beenary.persistence.utility.EntityMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents product DB entity.
 */
@Audited
@Entity
@Table(name = Tables.PRODUCT)
public class ProductDb extends BaseEntity<ProductDb> {

    @Column(name = ColumnName.CODE, nullable = false)
    private String code;

    @Column(name = ColumnName.NAME, nullable = false)
    private String name;

    @Column(name = ColumnName.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = ColumnName.PRICE, nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = ColumnName.VAT, nullable = false)
    private Integer vat;

    @Column(name = ColumnName.CURRENCY, nullable = false)
    private String currency;

    @Column(name = ColumnName.CATEGORY, nullable = false)
    private String category;

    @Column(name = ColumnName.STOCK_QUANTITY, nullable = false)
    private Integer stockQuantity;

    @Column(name = ColumnName.ENABLED, nullable = false)
    private boolean enabled;

    @Column(name = ColumnName.DELETED, nullable = false)
    private boolean deleted;

    /**
     * Instantiates new Db authority.
     */
    public ProductDb() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(final Integer vat) {
        this.vat = vat;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(final Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }

    public static ApiMapper<ProductDb, Product> apiMapper() {
        return entity -> {
            final Product product = new Product();
            product.setId(entity.getId());
            product.setCode(entity.getCode());
            product.setName(entity.getName());
            product.setDescription(entity.getDescription());
            product.setPrice(entity.getPrice());
            product.setVat(entity.getVat());
            product.setCurrency(entity.getCurrency());
            product.setCategory(entity.getCategory());
            product.setStockQuantity(entity.getStockQuantity());
            product.setEnabled(entity.isEnabled());
            product.setCreatedAt(entity.getCreatedAt());
            product.setUpdatedAt(entity.getUpdatedAt());
            return product;
        };
    }

    public static EntityMapper<Product, ProductDb> entityMapper() {
        return dto -> {
            final ProductDb product = new ProductDb();
            product.setId(dto.getId());
            product.setCode(dto.getCode());
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setVat(dto.getVat());
            product.setCurrency(dto.getCurrency());
            product.setCategory(dto.getCategory());
            product.setStockQuantity(dto.getStockQuantity());
            product.setEnabled(dto.getEnabled());
            return product;
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

        return ((ProductDb) object).getId().equals(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendFields(final StringBuilder sb) {
        sb.append(FieldName.asAppendable(FieldName.CODE)).append(getCode());
        sb.append(FieldName.asAppendable(FieldName.NAME)).append(getName());
        sb.append(FieldName.asAppendable(FieldName.PRICE)).append(getPrice());
        sb.append(FieldName.asAppendable(FieldName.STOCK_QUANTITY)).append(getStockQuantity());
        sb.append(FieldName.asAppendable(FieldName.ENABLED)).append(isEnabled());
        sb.append(FieldName.asAppendable(FieldName.DELETED)).append(isDeleted());
    }
}
