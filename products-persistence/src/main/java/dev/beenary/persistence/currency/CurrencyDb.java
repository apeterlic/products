package dev.beenary.persistence.currency;

import dev.beenary.api.Resource;
import dev.beenary.common.utility.ApiMapper;
import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.Tables;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents currency DB entity.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = Tables.CURRENCY)
public class CurrencyDb extends BaseEntity {

    @Column(name = ColumnName.CODE)
    private String value;

    public static ApiMapper<Resource, CurrencyDb> apiMapper() {
        return entity -> {
            final Resource product = new Resource();
            product.setName(entity.getValue());
            return product;
        };
    }

}
