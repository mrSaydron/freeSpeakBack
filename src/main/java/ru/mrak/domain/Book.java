package ru.mrak.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "source")
    private String source;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @NotNull
    @Column(name = "public_book", nullable = false)
    private Boolean publicBook;

    @OneToOne
    @JoinColumn(unique = true)
    private Dictionary dictionary;

    @ManyToOne
    @JsonIgnoreProperties(value = "books", allowSetters = true)
    private User loadedUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Book title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public Book author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public Book source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public Book text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isPublicBook() {
        return publicBook;
    }

    public Book publicBook(Boolean publicBook) {
        this.publicBook = publicBook;
        return this;
    }

    public void setPublicBook(Boolean publicBook) {
        this.publicBook = publicBook;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public Book dictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        return this;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public User getLoadedUser() {
        return loadedUser;
    }

    public Book loadedUser(User user) {
        this.loadedUser = user;
        return this;
    }

    public void setLoadedUser(User user) {
        this.loadedUser = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return id != null && id.equals(((Book) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", source='" + getSource() + "'" +
            ", text='" + getText() + "'" +
            ", publicBook='" + isPublicBook() + "'" +
            "}";
    }
}
