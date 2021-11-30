package ru.mrak.service.mapper;


import org.mapstruct.Mapper;
import ru.mrak.model.entity.Book;
import ru.mrak.service.dto.BookDto;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDto}.
 */
@Mapper(componentModel = "spring")
public interface BookLightMapper extends EntityMapper<BookDto, Book> {
}
