package com.epam.esm.service;

import com.epam.esm.exception.InvalidParameterException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    List<UserResponseDto> findAll(int page, int size) throws InvalidParameterException;
    UserResponseDto findById(long id) throws NotFoundEntityException;
    UserResponseDto findByLogin(String login) throws NotFoundEntityException;
    void addSpentMoney(long id, BigDecimal addedValue) throws NotFoundEntityException;
    UserResponseDto register(UserDto userDto);
    void delete(long id);
}
