package com.epam.esm.mapper;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper extends BaseMapper<TagDto, Tag> {
}
