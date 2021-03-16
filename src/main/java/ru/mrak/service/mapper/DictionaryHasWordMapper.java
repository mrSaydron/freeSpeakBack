package ru.mrak.service.mapper;


import ru.mrak.domain.*;
import ru.mrak.service.dto.DictionaryHasWordDTO;

import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link DictionaryHasWord} and its DTO {@link DictionaryHasWordDTO}.
 */
@Mapper(componentModel = "spring", uses = {WordMapper.class})
public interface DictionaryHasWordMapper extends EntityMapper<DictionaryHasWordDTO, DictionaryHasWord> {

    DictionaryHasWordDTO toDto(DictionaryHasWord dictionaryHasWord);

    default DictionaryHasWord fromId(Long id) {
        if (id == null) {
            return null;
        }
        DictionaryHasWord dictionaryHasWord = new DictionaryHasWord();
        dictionaryHasWord.setId(id);
        return dictionaryHasWord;
    }
}
