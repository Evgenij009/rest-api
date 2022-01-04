package com.epam.esm.repository;

import com.epam.esm.model.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends BaseEntity> {
    T create(T entity);
    List<T> findAllWithPagination(int page, int size);
    Optional<T> findById(long id);
    Optional<T> findByField(String columnName, Object value);
    T update(T entity);
    void deleteById(long id);
}
