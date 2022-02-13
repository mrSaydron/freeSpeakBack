package ru.mrak.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Класс с предложением из книги для прочтения
 */
@Getter
@Setter
@Accessors(chain = true)
public class BookSentenceReadDto {

    private Long id;
    private List<BookSentenceHasWordDTO> words;
    private BookDto book;
    private List<BookSentenceDTO> beforeSentences;
    private List<BookSentenceDTO> afterSentences;

}
