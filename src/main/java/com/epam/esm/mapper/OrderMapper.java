package com.epam.esm.mapper;

import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Order;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderDto, Order> {

}
