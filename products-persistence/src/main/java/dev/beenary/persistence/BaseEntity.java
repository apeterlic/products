package dev.beenary.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents base DB entity.
 */
@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<T>> implements Serializable, Comparable<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = ColumnName.ID, updatable = false, nullable = false)
    private UUID id;

    @CreationTimestamp
    @Column(name = ColumnName.CREATED_AT, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = ColumnName.UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Instantiates new base entity.
     */
    protected BaseEntity() {
        super();
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Compares this entity to another entity of the same type by their IDs.
     *
     * @param comparable the entity to compare with
     * @return comparison result [int] :: negative if less, zero if equal, positive if greater
     */
    @Override
    public int compareTo(@NonNull final T comparable) {
        if (getId() == null) {
            return (comparable.getId() == null ? 0 : -1);
        }
        return getId().compareTo(comparable.getId());
    }

    /**
     * Returns the hash code for this entity based on its ID.
     *
     * @return hashCode [int] :: the hash code
     */
    @Override
    public int hashCode() {
        if (getId() == null) {
            return super.hashCode();
        }

        final int prime = 31;
        int result = 1;
        result = prime * result + getId().hashCode();
        return result;
    }

    /**
     * Compares this entity to the specified object for equality.
     *
     * @param object the object to compare with
     * @return isEqual [boolean] :: {@code true} if equal; {@code false} otherwise
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(final Object object) {
        if (getId() == null) {
            return super.equals(object);
        }
        if (this == object) return true;
        if (object == null || !getClass().equals(object.getClass())) return false;
        return compareTo((T) object) == 0;
    }

    /**
     * Returns a String representation of the entity.
     *
     * @return str [{@link String}] :: the string representation
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName());
        sb.append("[");
        sb.append(FieldName.asAppendable(FieldName.ID)).append(getId());
        sb.append(FieldName.asAppendable(FieldName.CREATED_AT)).append(getCreatedAt());
        sb.append(FieldName.asAppendable(FieldName.UPDATED_AT)).append(getUpdatedAt());
        this.appendFields(sb);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Appends custom fields to the {@link #toString()} representation.
     *
     * @param sb [{@link StringBuilder}] :: the string builder
     */
    protected abstract void appendFields(final StringBuilder sb);

}
