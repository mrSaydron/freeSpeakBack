package ru.mrak.mapper;

import org.mapstruct.Mapper;
import ru.mrak.dto.BookCreateDTO;
import ru.mrak.model.entity.Book;

@Mapper(componentModel = "spring", uses = {TextTagMapper.class})
public interface BookCreateMapper extends EntityMapper<BookCreateDTO, Book> {
}
