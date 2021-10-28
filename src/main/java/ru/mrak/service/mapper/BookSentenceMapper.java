package ru.mrak.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.service.dto.BookSentenceDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookSentenceHasWordMapper.class})
public interface BookSentenceMapper extends EntityMapper<BookSentenceDTO, BookSentence> {

    @Override
    default BookSentence toEntity(BookSentenceDTO dto) {
        throw new RuntimeException();
    }

    @Override
    default List<BookSentence> toEntity(List<BookSentenceDTO> dtoList) {
        throw new RuntimeException();
    }
}
