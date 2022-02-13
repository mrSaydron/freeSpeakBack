package ru.mrak.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mrak.dto.BookSentenceDTO;
import ru.mrak.dto.BookSentenceReadDto;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.repository.BookSentenceRepository;
import ru.mrak.config.Constants;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {BookSentenceHasWordMapper.class, BookLightMapper.class, BookSentenceMapper.class})
public abstract class BookSentenceReadMapper implements EntityMapper<BookSentenceReadDto, BookSentence> {

    @Autowired private BookSentenceMapper bookSentenceMapper;

    @Autowired private BookSentenceRepository bookSentenceRepository;

    @Mapping(target = "beforeSentences", expression = "java( setBeforeSentences(entity) )")
    @Mapping(target = "afterSentences", expression = "java( setAfterSentences(entity) )")
    public abstract BookSentenceReadDto toDto(BookSentence entity);

    protected List<BookSentenceDTO> setBeforeSentences(BookSentence entity) {
        List<BookSentence> beforeSentences = bookSentenceRepository.findBeforeSentences(entity.getId(), entity.getBook().getId(), Constants.SENTENCES_BEFORE);
        return bookSentenceMapper.toDto(beforeSentences);
    }

    protected List<BookSentenceDTO> setAfterSentences(BookSentence entity) {
        List<BookSentence> afterSentences = bookSentenceRepository.findAfterSentences(entity.getId(), entity.getBook().getId(), Constants.SENTENCES_BEFORE);
        return bookSentenceMapper.toDto(afterSentences);
    }

}
