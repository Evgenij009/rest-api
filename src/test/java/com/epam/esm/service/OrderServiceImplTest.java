package com.epam.esm.service;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.mapper.OrderMapperImpl;
import com.epam.esm.mapper.UserMapperImpl;
import com.epam.esm.mapper.UserResponseMapperImpl;
import com.epam.esm.model.dto.CreateOrderDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.repository.impl.OrderRepositoryImpl;
import com.epam.esm.repository.impl.RoleRepositoryImpl;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.impl.OrderServiceImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OrderServiceImpl.class, OrderMapperImpl.class,
        UserServiceImpl.class, UserMapperImpl.class, UserResponseMapperImpl.class})
public class OrderServiceImplTest {
    private static final long ID = 1;
    private static final String NAME = "order";
    private static final User USER = new User(ID, NAME);
    private static final GiftCertificate GIFT_CERTIFICATE = new GiftCertificate();
    private static final Order ORDER = new Order(ID, USER, GIFT_CERTIFICATE, ZonedDateTime.now(),
            BigDecimal.ONE);
    private static final CreateOrderDto CREATE_ORDER_DTO = new CreateOrderDto(ID, ID);

    static {
        USER.setOrders(Collections.singletonList(ORDER));
    }

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 50;

    @MockBean
    private OrderRepositoryImpl orderRepository;

    @MockBean
    private GiftCertificateRepositoryImpl certificateRepository;

    @MockBean
    private UserRepositoryImpl userRepository;

    @MockBean
    private RoleRepositoryImpl roleRepository;

    @Autowired
    private OrderServiceImpl orderService;

//    @Test
//    public void testCreateShouldCreateWhenNotExist() {
//        when(userRepository.findById(ID)).thenReturn(Optional.of(USER));
//        when(certificateRepository.findById(ID)).thenReturn(Optional.of(GIFT_CERTIFICATE));
//        orderService.create(CREATE_ORDER_DTO);
//        verify(orderRepository).create(any());
//    }

    @Test(expected = NotFoundEntityException.class)
    public void testCreateShouldThrowsNoSuchEntityExceptionWhenUserNotFound() {
        when(certificateRepository.findById(ID)).thenReturn(Optional.of(GIFT_CERTIFICATE));
        orderService.create(CREATE_ORDER_DTO);
    }

    @Test(expected = NotFoundEntityException.class)
    public void testCreateShouldThrowsNoSuchEntityExceptionWhenCertificateNotFound() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(USER));
        orderService.create(CREATE_ORDER_DTO);
    }

    @Test(expected = ValidationException.class)
    public void testGetAllShouldThrowsInvalidParametersExceptionWhenPaginationInvalid() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(USER));
        orderService.findAllByUserId(ID, -3, -4);
    }

    @Test(expected = NotFoundEntityException.class)
    public void testGetByUserIdShouldThrowsInvalidParametersExceptionWhenUserNotFound() {
        when(orderRepository.findById(ID)).thenReturn(Optional.of(ORDER));
        when(userRepository.findById(ID)).thenReturn(Optional.empty());
        orderService.findByUserId(ID, ID);
    }

    @Test(expected = NotFoundEntityException.class)
    public void testGetByUserIdShouldThrowsInvalidParametersExceptionWhenOrderNotFound() {
        when(orderRepository.findById(ID)).thenReturn(Optional.empty());
        orderService.findByUserId(ID, ID);
    }
}
