package dev.beenary.persistence.category;

import dev.beenary.api.Resource;
import dev.beenary.common.utility.ApiMapper;
import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents category DB entity.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = Table.CATEGORY)
public class CategoryDb extends BaseEntity {

    @Column(name = ColumnName.NAME)
    private String value;

    public static ApiMapper<Resource, CategoryDb> apiMapper() {
        return entity -> {
            final Resource product = new Resource();
            product.setName(entity.getValue());
            return product;
        };
    }
}
