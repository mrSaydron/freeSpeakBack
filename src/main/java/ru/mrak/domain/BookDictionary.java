package ru.mrak.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * A Dictionary.
 */
@Entity
@Table(name = "book_dictionary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BookDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_language")
    private String baseLanguage;

    @Column(name = "target_language")
    private String targetLanguage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "dictionary", fetch = FetchType.LAZY)
    private List<BookDictionaryHasWord> dictionaryWords;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaseLanguage() {
        return baseLanguage;
    }

    public void setBaseLanguage(String baseLanguage) {
        this.baseLanguage = baseLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<BookDictionaryHasWord> getDictionaryWords() {
        return dictionaryWords;
    }

    public void setDictionaryWords(List<BookDictionaryHasWord> dictionaryWords) {
        this.dictionaryWords = dictionaryWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookDictionary)) {
            return false;
        }
        return id != null && id.equals(((BookDictionary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dictionary{" +
            "id=" + getId() +
            ", baseLanguage='" + getBaseLanguage() + "'" +
            ", targetLanguage='" + getTargetLanguage() + "'" +
            "}";
    }
}
