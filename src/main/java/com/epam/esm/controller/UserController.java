package com.epam.esm.controller;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.InvalidParameterException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.exception.ValidationExceptionChecker;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserResponseDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.link.OrderLinkProvider;
import com.epam.esm.util.link.UserLinkProvider;
import com.epam.esm.validator.RequestParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.util.RequestParams.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final UserLinkProvider userLinkProvider;
    private final OrderLinkProvider orderLinkProvider;

    @Autowired
    public UserController(UserService userService,
                          OrderService orderService,
                          UserLinkProvider userLinkProvider,
                          OrderLinkProvider orderLinkProvider) {
        this.userService = userService;
        this.orderService = orderService;
        this.userLinkProvider = userLinkProvider;
        this.orderLinkProvider = orderLinkProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping("/signup")
    public UserResponseDto signup(@RequestBody @Valid UserDto userDto, BindingResult bindingResult)
            throws DuplicateEntityException {
        ValidationExceptionChecker.checkDtoValid(bindingResult);
        UserResponseDto newUserDto = userService.register(userDto);
        userLinkProvider.provideLinks(newUserDto);
        return newUserDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> findAll(
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size)
            throws InvalidParameterException {
        RequestParametersValidator.validatePaginationParams(page, size);
        List<UserResponseDto> userDtoList = userService.findAll(page, size);
        return userDtoList.stream()
                .peek(userLinkProvider::provideLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto findById(@PathVariable long id) throws NotFoundEntityException {
        RequestParametersValidator.validateId(id);
        UserResponseDto userResponseDto = userService.findById(id);
        userLinkProvider.provideLinks(userResponseDto);
        return userResponseDto;
    }

    @GetMapping("/{id}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto findOrderByUserId(
            @PathVariable(value = "id") long id,
            @PathVariable(value = "orderId") long orderId) throws NotFoundEntityException {
        RequestParametersValidator.validateId(id);
        RequestParametersValidator.validateId(orderId);
        OrderDto orderDto = orderService.findByUserId(id, orderId);
        orderLinkProvider.provideLinks(orderDto);
        return orderDto;
    }

    @GetMapping("/{id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getOrdersByUserId(
            @PathVariable long id,
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_SIZE) int size
    ) throws InvalidParameterException, NotFoundEntityException {
        RequestParametersValidator.validateId(id);
        RequestParametersValidator.validatePaginationParams(page, size);
        List<OrderDto> orderDtoList = orderService.findAllByUserId(id, page, size);
        return orderDtoList.stream()
                .peek(orderLinkProvider::provideLinks)
                .collect(Collectors.toList());
    }
}
