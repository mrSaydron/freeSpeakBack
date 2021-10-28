package ru.mrak.model.entity.bookUserKnow;

import lombok.Getter;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.User;

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
