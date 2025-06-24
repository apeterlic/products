package dev.beenary.persistence.product;

import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.Table;
import dev.beenary.product.Product;
import dev.beenary.common.utility.ApiMapper;
import dev.beenary.common.utility.EntityMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Audited
@Data
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

}
