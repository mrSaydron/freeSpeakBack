package ru.mrak.service.mapper;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.User;
import ru.mrak.repository.BookUserKnowRepository;
import ru.mrak.service.UserService;
import ru.mrak.service.dto.BookDTO;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookLightMapper extends EntityMapper<BookDTO, Book> {
}
