package ru.mrak.service.dto;

import java.util.List;

/**
 * Класс с предложением из книги для прочтения
 */
public class BookSentenceReadDto {

    private Long id;
    private List<BookSentenceHasWordDTO> words;
    private BookDTO book;

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

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }
}
