package ru.mrak.mapper;

import org.mapstruct.Mapper;
import ru.mrak.model.entity.userWordProgress.UserWordHasProgress;
import ru.mrak.dto.userWord.WordProgressDTO;

/**
 * Mapper for the entity {@link UserWordHasProgress} and its DTO {@link WordProgressDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WordProgressMapper extends EntityMapper<WordProgressDTO, UserWordHasProgress> {
}
