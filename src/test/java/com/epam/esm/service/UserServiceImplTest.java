package com.epam.esm.service;

import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.mapper.UserMapperImpl;
import com.epam.esm.mapper.UserResponseMapperImpl;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.impl.RoleRepositoryImpl;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import com.epam.esm.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        UserServiceImpl.class,
        UserMapperImpl.class,
        UserResponseMapperImpl.class})
public class UserServiceImplTest {
    private static final long ID = 1;
    private static final String NAME = "user";
    private static final User USER = new User(ID, NAME);

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 50;

    @MockBean
    private UserRepositoryImpl userRepository;

    @MockBean
    private RoleRepositoryImpl roleRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test(expected = ValidationException.class)
    public void testGetAllShouldThrowsInvalidParametersExceptionWhenParamsInvalid() {
        userService.findAll(-3, -2);
    }

    @Test
    public void testGetByIdShouldGetWhenFound() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(USER));
        userService.findById(ID);
        verify(userRepository).findById(ID);
    }

    @Test(expected = NotFoundEntityException.class)
    public void testGetByIdShouldThrowsNoFoundEntityExceptionWhenNotFound() {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());
        userService.findById(ID);
    }
}
