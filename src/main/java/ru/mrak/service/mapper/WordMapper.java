package ru.mrak.service.mapper;


import ru.mrak.domain.*;
import ru.mrak.service.dto.WordDTO;

import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity {@link Word} and its DTO {@link WordDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WordMapper extends EntityMapper<WordDTO, Word> {

    default Word fromId(Long id) {
        if (id == null) {
            return null;
        }
        Word word = new Word();
        word.setId(id);
        return word;
    }
}
