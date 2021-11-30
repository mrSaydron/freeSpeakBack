package ru.mrak.service.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.bookUser.BookUser;
import ru.mrak.model.entity.bookUser.BookUserId;
import ru.mrak.model.entity.bookUserKnow.BookUserKnow;
import ru.mrak.repository.BookUserKnowRepository;
import ru.mrak.repository.BookUserRepository;
import ru.mrak.service.UserService;
import ru.mrak.service.dto.BookDto;

import org.mapstruct.*;

import java.util.Map;
import java.util.Optional;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDto}.
 */
@Mapper(componentModel = "spring")
public abstract class BookMapper implements EntityMapper<BookDto, Book> {

    @Autowired
    private UserService userService;

    @Autowired private BookUserKnowRepository bookUserKnowRepository;
    @Autowired private BookUserRepository bookUserRepository;

    @Mapping(target = "know", expression = "java( know(book) )")
    @Mapping(target = "isReading", expression = "java( isReading(book) )")
    public abstract BookDto toDto(Book book);

    public abstract Book toEntity(BookDto bookDTO);

    Double know(Book book) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("Что то пошло не так"));
        Optional<BookUserKnow> knowOptional = bookUserKnowRepository.findByUserAndBook(user, book);
        return knowOptional.map(BookUserKnow::getKnow).orElse(null);
    }

    Boolean isReading(Book book) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("Что то пошло не так"));
        Optional<BookUser> bookUserOptional = bookUserRepository.findById(new BookUserId(user.getId(), book.getId()));
        return bookUserOptional.map(BookUser::getIsReading).orElse(false);
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
