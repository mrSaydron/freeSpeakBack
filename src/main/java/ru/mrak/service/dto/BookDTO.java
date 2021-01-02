package ru.mrak.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ru.mrak.domain.Book} entity.
 */
public class BookDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String title;

    private String author;

    private String source;

    @NotNull
    private String text;

    @NotNull
    private Boolean publicBook;


    private Long dictionaryId;

    private Long loadedUserId;

    private String loadedUserLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isPublicBook() {
        return publicBook;
    }

    public void setPublicBook(Boolean publicBook) {
        this.publicBook = publicBook;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Long getLoadedUserId() {
        return loadedUserId;
    }

    public void setLoadedUserId(Long userId) {
        this.loadedUserId = userId;
    }

    public String getLoadedUserLogin() {
        return loadedUserLogin;
    }

    public void setLoadedUserLogin(String userLogin) {
        this.loadedUserLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookDTO)) {
            return false;
        }

        return id != null && id.equals(((BookDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", source='" + getSource() + "'" +
            ", text='" + getText() + "'" +
            ", publicBook='" + isPublicBook() + "'" +
            ", dictionaryId=" + getDictionaryId() +
            ", loadedUserId=" + getLoadedUserId() +
            ", loadedUserLogin='" + getLoadedUserLogin() + "'" +
            "}";
    }
}
