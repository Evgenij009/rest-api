package com.epam.esm.service;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.dto.CreateOrderDto;
import com.epam.esm.model.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto create(CreateOrderDto createOrderDto) throws NotFoundEntityException;
    List<OrderDto> findAllByUserId(long userId, int page, int size) throws NotFoundEntityException;
    OrderDto findByUserId(long userId, long orderId) throws NotFoundEntityException;
}
