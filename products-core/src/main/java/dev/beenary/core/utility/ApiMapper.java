package dev.beenary.core.utility;

import dev.beenary.persistence.BaseEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for mapping database entities to dto objects.
 *
 * @param <S> :: database entity.
 * @param <T> :: data transfer object.
 */
public interface ApiMapper<S extends BaseEntity, T> {

    T toDto(final S entity);

    @SuppressWarnings("java:S6204")
    default List<T> toModifieableDtoList(final List<S> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default List<T> toUnmodifieableDtoList(final List<S> entityList) {
        return entityList.stream()
                .map(this::toDto)
                .toList();
    }
}
