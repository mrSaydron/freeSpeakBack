package ru.mrak.service.mapper;


import ru.mrak.domain.*;
import ru.mrak.service.dto.DictionaryHasWordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BookDictionaryHasWord} and its DTO {@link DictionaryHasWordDTO}.
 */
@Mapper(componentModel = "spring", uses = {WordMapper.class})
public interface DictionaryHasWordMapper extends EntityMapper<DictionaryHasWordDTO, BookDictionaryHasWord> {

    DictionaryHasWordDTO toDto(BookDictionaryHasWord dictionaryHasWord);

    default BookDictionaryHasWord fromId(Long id) {
        if (id == null) {
            return null;
        }
        BookDictionaryHasWord dictionaryHasWord = new BookDictionaryHasWord();
        dictionaryHasWord.setId(id);
        return dictionaryHasWord;
    }
}
