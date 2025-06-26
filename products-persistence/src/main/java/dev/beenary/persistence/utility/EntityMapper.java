package dev.beenary.persistence.utility;

import dev.beenary.persistence.BaseEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for mapping dto objects to database entities.
 *
 * @param <S> :: data transfer object.
 * @param <T> :: database entity.
 */
public interface EntityMapper<S, T extends BaseEntity> {

    T toEntity(final S entity);

    @SuppressWarnings("java:S6204")
    default List<T> toModifieableEntityList(final List<S> entityList) {
        return entityList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    default List<T> toUnmodifieableEntityList(final List<S> entityList) {
        return entityList.stream()
                .map(this::toEntity)
                .toList();
    }
}
