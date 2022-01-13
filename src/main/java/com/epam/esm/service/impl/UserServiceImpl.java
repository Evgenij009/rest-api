package com.epam.esm.service.impl;

import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NotFoundEntityException;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.mapper.UserResponseMapper;
import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserResponseDto;
import com.epam.esm.model.entity.Role;
import com.epam.esm.model.entity.Status;
import com.epam.esm.model.entity.User;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ColumnName;
import com.epam.esm.validator.RequestParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND = "user.not.found";
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserResponseMapper userResponseMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           UserResponseMapper userResponseMapper,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userResponseMapper = userResponseMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public List<UserResponseDto> findAll(int page, int size) {
        RequestParametersValidator.validatePaginationParams(page, size);
        return userResponseMapper.mapListToDto(userRepository.findAllWithPagination(page, size));
    }

    @Override
    @Transactional
    public UserResponseDto findById(long id) throws NotFoundEntityException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(USER_NOT_FOUND));
        return userResponseMapper.mapToDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto findByLogin(String login) throws NotFoundEntityException {
        User user = userRepository.findByField(ColumnName.LOGIN, login)
                .orElseThrow(() -> new NotFoundEntityException(USER_NOT_FOUND));
        return userResponseMapper.mapToDto(user);
    }

    @Override
    public void addSpentMoney(long id, BigDecimal addedValue) throws NotFoundEntityException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(USER_NOT_FOUND));
        user.setSpentMoney(user.getSpentMoney().add(addedValue));
        userRepository.update(user);
    }

    @Override
    @Transactional
    public UserResponseDto register(UserDto userDto) {
        User user = userMapper.mapToEntity(userDto);
        if (userRepository.findByField(ColumnName.LOGIN, user.getLogin()).isPresent()) {
            throw new DuplicateEntityException("user.exist");
        }
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundEntityException("role.not.found"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setRoles(userRoles);
        return userResponseMapper.mapToDto(userRepository.create(user));
    }

    @Override
    public void delete(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
