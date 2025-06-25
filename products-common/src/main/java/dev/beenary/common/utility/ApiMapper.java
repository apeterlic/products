package dev.beenary.common.utility;

import java.util.List;
import java.util.stream.Collectors;

public interface ApiMapper<T, S> {

    T toDto(final S entity);

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
