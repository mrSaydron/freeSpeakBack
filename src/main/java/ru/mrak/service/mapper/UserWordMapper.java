package ru.mrak.service.mapper;

import org.mapstruct.Mapper;
import ru.mrak.model.entity.userWordProgress.UserWordProgress;
import ru.mrak.service.dto.userWord.UserWordDTO;

/**
 * Mapper for the entity {@link UserHasWord} and its DTO {@link UserWordDTO}.
 */
@Mapper(componentModel = "spring", uses = {WordProgressMapper.class, WordMapper.class})
public interface UserWordMapper extends EntityMapper<UserWordDTO, UserWordProgress> {
}
