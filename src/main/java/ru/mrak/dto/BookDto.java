package ru.mrak.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mrak.model.entity.Book;

import java.io.Serializable;
import java.util.Collection;

/**
 * A DTO for the {@link Book} entity.
 */
@Getter
@Setter
@ToString
public class BookDto implements Serializable {

    private Long id;
    private String title;
    private String author;
    private String pictureId;
    private Double know;
    private Boolean isReading;
    private Collection<TextTagDto> textTags;

}
