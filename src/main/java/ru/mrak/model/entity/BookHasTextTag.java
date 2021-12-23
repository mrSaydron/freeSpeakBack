package ru.mrak.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * таблица: book_has_text_tag
 */
@Getter
@Setter
@Embeddable
public class BookHasTextTag {

    @Column(name = "text_tag_id")
    private long textTagId;
}
