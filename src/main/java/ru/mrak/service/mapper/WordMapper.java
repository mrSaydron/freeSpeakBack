package ru.mrak.service.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import ru.mrak.model.entity.Word;
import ru.mrak.model.enumeration.ServiceDataKeysEnum;
import ru.mrak.service.ServiceDataService;
import ru.mrak.service.dto.WordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Word} and its DTO {@link WordDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public abstract class WordMapper implements EntityMapper<WordDTO, Word> {

    @Autowired
    ServiceDataService serviceDataService;

    public abstract WordDTO toDto(Word word);

    @AfterMapping
    void frequencyCalc(Word entity,
                       @MappingTarget WordDTO dto) {
        if (dto.getTotalAmount() != null) {
            Long totalWords = Long.valueOf(serviceDataService.getByKey(ServiceDataKeysEnum.totalWords).getValue());
            dto.setFrequency(dto.getTotalAmount() / 1.0 / totalWords);
        }
    }

    Word fromId(Long id) {
        if (id == null) {
            return null;
        }
        Word word = new Word();
        word.setId(id);
        return word;
    }
}
