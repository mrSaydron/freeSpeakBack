package ru.mrak.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;
import ru.mrak.config.Constants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_sentence")
@Getter
@Setter
public class BookSentence {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sentence_seq")
    @SequenceGenerator(name = "book_sentence_seq", sequenceName = "book_sentence_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "book_sentence_has_word",
        joinColumns = @JoinColumn(name = "book_sentence_id")
    )
    @CollectionId(
        columns = @Column(name = "id"),
        type = @Type(type = "long"),
        generator = "book_sentence_has_word_seq"
    )
    @SequenceGenerator(name = "book_sentence_has_word_seq", sequenceName = "book_sentence_has_word_seq", allocationSize = 1)
    private List<BookSentenceHasWord> words = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BookSentenceHasWord> getWords() {
        return words;
    }

    public void setWords(List<BookSentenceHasWord> words) {
        this.words = words;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
