package com.epam.esm.mapper;

import com.epam.esm.model.dto.UserDto;
import com.epam.esm.model.dto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoResponseMapper extends BaseMapper<UserResponseDto, UserDto> {
}
