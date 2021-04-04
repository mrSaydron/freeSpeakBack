package ru.mrak.service.mapper;

import org.mapstruct.Mapper;
import ru.mrak.domain.UserWordProgress;
import ru.mrak.service.dto.userWord.WordProgressDTO;

/**
 * Mapper for the entity {@link UserWordProgress} and its DTO {@link WordProgressDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WordProgressMapper extends EntityMapper<WordProgressDTO, UserWordProgress> {
}
