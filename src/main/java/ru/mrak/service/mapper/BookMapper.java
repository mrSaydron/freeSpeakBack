package ru.mrak.service.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.domain.*;
import ru.mrak.repository.BookUserKnowRepository;
import ru.mrak.service.UserService;
import ru.mrak.service.dto.BookDTO;

import org.mapstruct.*;

import javax.persistence.EntityManager;
import java.util.Optional;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring", uses = {DictionaryMapper.class, UserMapper.class})
public abstract class BookMapper implements EntityMapper<BookDTO, Book> {

    @Autowired
    private UserService userService;

    @Autowired
    private BookUserKnowRepository bookUserKnowRepository;

    @Autowired
    private EntityManager entityManager;

    @Mapping(source = "dictionary.id", target = "dictionaryId")
    @Mapping(source = "loadedUser.id", target = "loadedUserId")
    @Mapping(source = "loadedUser.login", target = "loadedUserLogin")
    public abstract BookDTO toDto(Book book);

    @Mapping(source = "dictionaryId", target = "dictionary")
    @Mapping(source = "loadedUserId", target = "loadedUser")
    @Mapping(target = "removeUser", ignore = true)
    public abstract Book toEntity(BookDTO bookDTO);

    @AfterMapping
    void knowWrite(Book entity, @MappingTarget BookDTO dto) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("Что то пошло не так"));
        bookUserKnowRepository.findByUserAndBookAndBookDictionaryId(user, entity, entity.getDictionary().getId())
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
