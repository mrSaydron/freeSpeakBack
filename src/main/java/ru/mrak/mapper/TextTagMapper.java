package ru.mrak.mapper;

import org.mapstruct.Mapper;
import ru.mrak.model.entity.TextTag;
import ru.mrak.dto.TextTagDto;

@Mapper(componentModel = "spring")
public interface TextTagMapper extends EntityMapper<TextTagDto, TextTag> {
}
