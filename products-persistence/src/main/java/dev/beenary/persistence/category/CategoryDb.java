package dev.beenary.persistence.category;

import dev.beenary.api.Resource;
import dev.beenary.persistence.utility.ApiMapper;
import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.Tables;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents category DB entity.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = Tables.CATEGORY)
public class CategoryDb extends BaseEntity {

    @Column(name = ColumnName.NAME)
    private String value;

    public static ApiMapper<CategoryDb, Resource> apiMapper() {
        return entity -> {
            final Resource product = new Resource();
            product.setName(entity.getValue());
            return product;
        };
    }
}
