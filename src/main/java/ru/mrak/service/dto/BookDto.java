package ru.mrak.service.dto;

import lombok.Getter;
import lombok.Setter;
import ru.mrak.model.entity.Book;

import java.io.Serializable;

/**
 * A DTO for the {@link Book} entity.
 */
@Getter
@Setter
public class BookDto implements Serializable {

    private Long id;
    private String title;
    private String author;
    private String pictureId;
    private Double know;
    private Boolean isReading;

    // prettier-ignore
    @Override
    public String toString() {
        return "BookDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", know='" + getKnow() + "'" +
            "}";
    }
}
