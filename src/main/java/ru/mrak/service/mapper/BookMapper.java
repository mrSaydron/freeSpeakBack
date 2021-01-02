package ru.mrak.service.mapper;


import ru.mrak.domain.*;
import ru.mrak.service.dto.BookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring", uses = {DictionaryMapper.class})
public interface BookMapper extends EntityMapper<BookDTO, Book> {

    @Mapping(source = "dictionary.id", target = "dictionaryId")
    BookDTO toDto(Book book);

    @Mapping(source = "dictionaryId", target = "dictionary")
    Book toEntity(BookDTO bookDTO);

    default Book fromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
