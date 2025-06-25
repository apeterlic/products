package dev.beenary.common.utility;

import java.util.List;
import java.util.stream.Collectors;

public interface EntityMapper<T, S> {

    T toEntity(final S entity);

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
