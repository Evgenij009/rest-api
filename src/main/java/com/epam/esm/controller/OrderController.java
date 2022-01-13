package com.epam.esm.controller;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.exception.ValidationExceptionChecker;
import com.epam.esm.model.dto.CreateOrderDto;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.link.OrderLinkProvider;
import com.epam.esm.util.security.UserAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderLinkProvider orderLinkProvider;
    private final UserAccessService userAccessService;

    @Autowired
    public OrderController(OrderService orderService, OrderLinkProvider orderLinkProvider,
                           UserAccessService userAccessService) {
        this.orderService = orderService;
        this.orderLinkProvider = orderLinkProvider;
        this.userAccessService = userAccessService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(HttpServletRequest httpServletRequest,
                                @RequestBody @Valid CreateOrderDto createOrderDto,
                                BindingResult bindingResult) throws NotFoundEntityException {
        ValidationExceptionChecker.checkDtoValid(bindingResult);
        userAccessService.checkAccess(httpServletRequest, createOrderDto.getUserId());
        OrderDto orderDto = orderService.create(createOrderDto);
        orderLinkProvider.provideLinks(orderDto);
        return orderDto;
    }
}
