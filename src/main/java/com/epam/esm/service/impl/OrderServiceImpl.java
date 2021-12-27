package com.epam.esm.service.impl;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.dto.CreateOrderDto;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String ORDER_NOT_FOUND = "order.not.found";
    private static final String USER_NOT_FOUND = "user.not.found";
    private final OrderRepository orderRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            GiftCertificateRepository giftCertificateRepository,
                            UserRepository userRepository,
                            UserService userService,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.giftCertificateRepository = giftCertificateRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional(rollbackFor = NotFoundEntityException.class)
    public OrderDto create(CreateOrderDto createOrderDto) throws NotFoundEntityException {
        Order order = new Order();
        User user = userRepository.findById(createOrderDto.getUserId())
                        .orElseThrow(() -> new NotFoundEntityException(USER_NOT_FOUND));
        order.setUser(user);
        GiftCertificate giftCertificate = giftCertificateRepository.findById(createOrderDto.getCertificateId())
                .orElseThrow(() -> new NotFoundEntityException("certificate.not.found"));
        order.setGiftCertificate(giftCertificate);
        order.setCost(giftCertificate.getPrice());
        userService.addSpentMoney(createOrderDto.getUserId(), order.getCost());
        return orderMapper.mapToDto(orderRepository.create(order));
    }

    @Override
    @Transactional
    public List<OrderDto> findAllByUserId(long userId, int page, int size) throws NotFoundEntityException {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundEntityException(USER_NOT_FOUND));
        return orderMapper.mapListToDto(orderRepository.findAllByUserId(userId, page, size));
    }

    @Override
    @Transactional
    public OrderDto findByUserId(long userId, long orderId) throws NotFoundEntityException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundEntityException(ORDER_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundEntityException(USER_NOT_FOUND));
        List<Order> orders = user.getOrders();
        if (orders == null || orders.isEmpty() || !orders.contains(orders)) {
            throw new NotFoundEntityException(ORDER_NOT_FOUND);
        }
        return orderMapper.mapToDto(order);
    }
}
