package ru.mrak.service.mapper;

import org.mapstruct.Mapper;
import ru.mrak.domain.UserDictionaryHasWord;
import ru.mrak.service.dto.userWord.UserWordDTO;

/**
 * Mapper for the entity {@link UserDictionaryHasWord} and its DTO {@link UserWordDTO}.
 */
@Mapper(componentModel = "spring", uses = {WordProgressMapper.class, WordMapper.class})
public interface UserWordMapper extends EntityMapper<UserWordDTO, UserDictionaryHasWord> {
}
