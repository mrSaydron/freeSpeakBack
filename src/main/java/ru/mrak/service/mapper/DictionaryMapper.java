package ru.mrak.service.mapper;


import ru.mrak.domain.*;
import ru.mrak.service.dto.BookDictionaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BookDictionary} and its DTO {@link BookDictionaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {DictionaryHasWordMapper.class})
public interface DictionaryMapper extends EntityMapper<BookDictionaryDTO, BookDictionary> {

    BookDictionaryDTO toDto(BookDictionary dictionary);

    default BookDictionary fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookDictionary dictionary = new BookDictionary();
        dictionary.setId(id);
        return dictionary;
    }
}
