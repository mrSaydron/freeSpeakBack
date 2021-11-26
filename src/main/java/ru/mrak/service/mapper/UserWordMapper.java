package ru.mrak.service.mapper;

import org.mapstruct.Mapper;
import ru.mrak.model.entity.userWordProgress.UserWord;
import ru.mrak.service.dto.userWord.UserWordDto;

/**
 * Mapper for the entity {@link UserHasWord} and its DTO {@link UserWordDto}.
 */
@Mapper(componentModel = "spring", uses = {WordProgressMapper.class, WordMapper.class})
public interface UserWordMapper extends EntityMapper<UserWordDto, UserWord> {
}
