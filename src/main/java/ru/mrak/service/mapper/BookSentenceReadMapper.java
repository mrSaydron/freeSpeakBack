package ru.mrak.service.mapper;

import org.mapstruct.Mapper;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.service.dto.BookSentenceReadDto;

@Mapper(componentModel = "spring", uses = {BookSentenceHasWordMapper.class, BookLightMapper.class})
public interface BookSentenceReadMapper extends EntityMapper<BookSentenceReadDto, BookSentence> {
}
