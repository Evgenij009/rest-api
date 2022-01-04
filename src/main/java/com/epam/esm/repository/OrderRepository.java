package com.epam.esm.repository;

import com.epam.esm.model.entity.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order>{
    List<Order> findAllByUserId(long userId, int page, int size);
    List<Order> findAllByUserId(long userId);
}
