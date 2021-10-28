package ru.mrak.service.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.User;
import ru.mrak.repository.BookUserKnowRepository;
import ru.mrak.service.UserService;
import ru.mrak.service.dto.BookDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring")
public abstract class BookMapper implements EntityMapper<BookDTO, Book> {

    @Autowired
    private UserService userService;

    @Autowired
    private BookUserKnowRepository bookUserKnowRepository;

    public abstract BookDTO toDto(Book book);

    public abstract Book toEntity(BookDTO bookDTO);

    @AfterMapping
    void knowWrite(Book entity, @MappingTarget BookDTO dto) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("Что то пошло не так"));
        bookUserKnowRepository.findByUserAndBook(user, entity)
            .ifPresent(bookUserKnow -> dto.setKnow(bookUserKnow.getKnow()));
    }

    public Book fromId(Long id) {
        if (id == null) {
            return null;
        }
        Book book = new Book();
        book.setId(id);
        return book;
    }
}
