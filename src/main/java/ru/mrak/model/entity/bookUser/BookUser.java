package ru.mrak.model.entity.bookUser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@IdClass(BookUserId.class)
@Getter
@Setter
public class BookUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;

    @Column(name = "is_reading")
    private Boolean isReading;

    @Column(name = "last_read_sentence_id")
    private Long lastReadSentenceId;

    @Column(name = "bookmark_sentence_id")
    private Long bookmarkSentenceId;

    @Column(name = "finish_date")
    private LocalDateTime finishDate;

    public BookUser(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "BookUser{" +
            "userId=" + userId +
            ", bookId=" + bookId +
            '}';
    }
}
