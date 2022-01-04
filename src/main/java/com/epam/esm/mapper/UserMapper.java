package com.epam.esm.mapper;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDto, User> {
}
