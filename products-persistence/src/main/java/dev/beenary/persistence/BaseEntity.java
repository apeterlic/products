package dev.beenary.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents base DB entity.
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {

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

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
