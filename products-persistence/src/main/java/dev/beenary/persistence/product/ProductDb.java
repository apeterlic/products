package dev.beenary.persistence.product;

import dev.beenary.common.utility.ApiMapper;
import dev.beenary.common.utility.EntityMapper;
import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.Table;
import dev.beenary.api.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents product DB entity.
 */
@Setter
@Getter
@Audited
@Entity(name = Table.PRODUCT)
public class ProductDb extends BaseEntity {

    @Column(name = ColumnName.CODE, nullable = false)
    private String code;

    @Column(name = ColumnName.NAME, nullable = false)
    private String name;

    @Column(name = ColumnName.DESCRIPTION, nullable = false)
    private String description;

    @Column(name = ColumnName.PRICE, nullable = false)
    private BigDecimal price;

    @Column(name = ColumnName.VAT, nullable = false)
    private Integer vat;

    @Column(name = ColumnName.CURRENCY, nullable = false)
    private String currency;

    @Column(name = ColumnName.CATEGORY, nullable = false)
    private String category;

    @Column(name = ColumnName.STOCK_QUANTITY, nullable = false)
    private Long stockQuantity;

    @Column(name = ColumnName.ENABLED, nullable = false)
    private boolean enabled;

    // @Version
    @Column(name = ColumnName.VERSION)
    private Long version;

    @Column(name = ColumnName.DELETED, nullable = false)
    private boolean deleted;

    public static ApiMapper<Product, ProductDb> apiMapper() {
        return entity -> {
            final Product product = new Product();
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

    public static EntityMapper<ProductDb, Product> entityMapper() {
        return dto -> {
            final ProductDb product = new ProductDb();
            product.setCode(dto.getCode());
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setVat(dto.getVat());
            product.setCurrency(dto.getCurrency());
            product.setCategory(dto.getCategory());
            product.setStockQuantity(dto.getStockQuantity());
            product.setEnabled(dto.getEnabled());
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());
            return product;
        };
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final ProductDb productDb = (ProductDb) o;
        return enabled == productDb.enabled && deleted == productDb.deleted && code.equals(productDb.code) && name.equals(productDb.name) && description.equals(productDb.description) && price.equals(productDb.price) && vat.equals(productDb.vat) && currency.equals(productDb.currency) && category.equals(productDb.category) && Objects.equals(stockQuantity, productDb.stockQuantity) && Objects.equals(version, productDb.version);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + vat.hashCode();
        result = 31 * result + currency.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + Objects.hashCode(stockQuantity);
        result = 31 * result + Boolean.hashCode(enabled);
        result = 31 * result + Objects.hashCode(version);
        result = 31 * result + Boolean.hashCode(deleted);
        return result;
    }

    @Override
    public String toString() {
        return "ProductDb{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", vat=" + vat +
                ", currency='" + currency + '\'' +
                ", category='" + category + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", enabled=" + enabled +
                ", version=" + version +
                ", deleted=" + deleted +
                '}';
    }
}
