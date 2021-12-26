package ru.mrak.mapper;


import org.mapstruct.Mapper;
import ru.mrak.model.entity.Book;
import ru.mrak.dto.BookDto;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDto}.
 */
@Mapper(componentModel = "spring", uses = {TextTagMapper.class})
public interface BookLightMapper extends EntityMapper<BookDto, Book> {
}
