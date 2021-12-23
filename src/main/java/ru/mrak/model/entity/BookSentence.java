package ru.mrak.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

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
    private Collection<BookSentenceHasWord> words = new ArrayList<>();

}
