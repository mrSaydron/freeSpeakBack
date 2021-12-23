package ru.mrak.service.mapper;

import org.mapstruct.Mapper;
import ru.mrak.model.entity.TextTag;
import ru.mrak.service.dto.TextTagDto;

@Mapper(componentModel = "spring")
public interface TextTagMapper extends EntityMapper<TextTagDto, TextTag> {
}
