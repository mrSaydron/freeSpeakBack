package ru.mrak.dto;

import java.util.List;

/**
 * Класс с предложением из книги для прочтения
 */
public class BookSentenceReadDto {

    private Long id;
    private List<BookSentenceHasWordDTO> words;
    private BookDto book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BookSentenceHasWordDTO> getWords() {
        return words;
    }

    public void setWords(List<BookSentenceHasWordDTO> words) {
        this.words = words;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }
}
