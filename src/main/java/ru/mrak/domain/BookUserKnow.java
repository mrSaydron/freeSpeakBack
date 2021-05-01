package ru.mrak.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "book_user_know")
@IdClass(BookUserKnowId.class)
@Getter
public class BookUserKnow {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Id
    @Column(name = "book_dictionary_id", nullable = false)
    private Long bookDictionaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;

    @Column(name = "know")
    private Double know;

    @Override
    public String toString() {
        return "BookUserKnow{" +
            "know=" + know +
            '}';
    }
}
