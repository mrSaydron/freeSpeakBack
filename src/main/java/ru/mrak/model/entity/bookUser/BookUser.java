package ru.mrak.model.entity.bookUser;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "book_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@IdClass(BookUserId.class)
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

//    @Column(name = "last_open_date")
//    private LocalDateTime lastOpenDate;

    public BookUser(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

//    public LocalDateTime getLastOpenDate() {
//        return lastOpenDate;
//    }
//
//    public void setLastOpenDate(LocalDateTime lastOpenDate) {
//        this.lastOpenDate = lastOpenDate;
//    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookUser bookUser = (BookUser) o;
        return userId.equals(bookUser.userId) &&
            bookId.equals(bookUser.bookId);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "BookUser{" +
            "userId=" + userId +
            ", bookId=" + bookId +
            '}';
    }
}
