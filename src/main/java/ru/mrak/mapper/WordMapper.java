package ru.mrak.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import ru.mrak.model.entity.Word;
import ru.mrak.model.enumeration.ServiceDataKeysEnum;
import ru.mrak.service.ServiceDataService;
import ru.mrak.dto.WordDto;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Word} and its DTO {@link WordDto}.
 */
@Mapper(componentModel = "spring", uses = {})
public abstract class WordMapper implements EntityMapper<WordDto, Word> {

    @Autowired
    ServiceDataService serviceDataService;

    public abstract WordDto toDto(Word word);

    @AfterMapping
    void frequencyCalc(Word entity,
                       @MappingTarget WordDto dto) {
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
