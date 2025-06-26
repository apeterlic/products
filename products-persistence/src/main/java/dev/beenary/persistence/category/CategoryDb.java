package dev.beenary.persistence.category;

import dev.beenary.api.Resource;
import dev.beenary.persistence.BaseEntity;
import dev.beenary.persistence.ColumnName;
import dev.beenary.persistence.FieldName;
import dev.beenary.persistence.Tables;
import dev.beenary.persistence.utility.ApiMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * Represents category DB entity.
 */
@Entity
@Table(name = Tables.CATEGORY)
public class CategoryDb extends BaseEntity<CategoryDb> {

    @Column(name = ColumnName.NAME)
    private String value;

    /**
     * Instantiates new DB category.
     */
    public CategoryDb() {
        super();
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public static ApiMapper<CategoryDb, Resource> apiMapper() {
        return entity -> {
            final Resource product = new Resource();
            product.setName(entity.getValue());
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

        return ((CategoryDb) object).getId().equals(getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void appendFields(final StringBuilder sb) {
        sb.append(FieldName.asAppendable(FieldName.VALUE)).append(getValue());
    }

}
