package ru.mrak.service.mapper;


import ru.mrak.domain.*;
import ru.mrak.service.dto.DictionaryHasWordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DictionaryHasWord} and its DTO {@link DictionaryHasWordDTO}.
 */
@Mapper(componentModel = "spring", uses = {DictionaryMapper.class, WordMapper.class})
public interface DictionaryHasWordMapper extends EntityMapper<DictionaryHasWordDTO, DictionaryHasWord> {

    @Mapping(source = "dictionary.id", target = "dictionaryId")
    @Mapping(source = "word.id", target = "wordId")
    DictionaryHasWordDTO toDto(DictionaryHasWord dictionaryHasWord);

    @Mapping(source = "dictionaryId", target = "dictionary")
    @Mapping(source = "wordId", target = "word")
    DictionaryHasWord toEntity(DictionaryHasWordDTO dictionaryHasWordDTO);

    default DictionaryHasWord fromId(Long id) {
        if (id == null) {
            return null;
        }
        DictionaryHasWord dictionaryHasWord = new DictionaryHasWord();
        dictionaryHasWord.setId(id);
        return dictionaryHasWord;
    }
}
