package com.epam.esm.controller;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.exception.ValidationExceptionChecker;
import com.epam.esm.model.dto.CreateOrderDto;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.link.OrderLinkProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderLinkProvider orderLinkProvider;

    @Autowired
    public OrderController(OrderService orderService, OrderLinkProvider orderLinkProvider) {
        this.orderService = orderService;
        this.orderLinkProvider = orderLinkProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody @Valid CreateOrderDto createOrderDto,
                                BindingResult bindingResult) throws NotFoundEntityException {
        ValidationExceptionChecker.checkDtoValid(bindingResult);
        OrderDto orderDto = orderService.create(createOrderDto);
        orderLinkProvider.provideLinks(orderDto);
        return orderDto;
    }
}
