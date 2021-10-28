package ru.mrak.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mrak.model.entity.BookSentenceHasWord;
import ru.mrak.service.dto.BookSentenceHasWordDTO;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface BookSentenceHasWordMapper extends EntityMapper<BookSentenceHasWordDTO, BookSentenceHasWord> {

    @Mapping(source = "translate.id", target = "translateId")
    BookSentenceHasWordDTO toDto(BookSentenceHasWord entity);

    @Override
    default BookSentenceHasWord toEntity(BookSentenceHasWordDTO dto) {
        throw new RuntimeException();
    }

    @Override
    default List<BookSentenceHasWord> toEntity(List<BookSentenceHasWordDTO> dtoList) {
        throw new RuntimeException();
    }
}
