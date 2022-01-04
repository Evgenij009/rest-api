package com.epam.esm.mapper;

import java.util.List;

public interface BaseMapper<T, V> {
    T mapToDto(V v);
    V mapToEntity(T t);
    List<T> mapListToDto(List<V> v);
    List<V> mapListToEntity(List<T> t);
}
