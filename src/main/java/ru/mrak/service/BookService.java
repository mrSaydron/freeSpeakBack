package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import ru.mrak.domain.*;
import ru.mrak.repository.BookRepository;
import ru.mrak.repository.BookUserRepository;
import ru.mrak.repository.UserRepository;
import ru.mrak.service.dto.BookDTO;
import ru.mrak.service.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Book}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookMapper bookMapper;

    private final UserService userService;
    private final DictionaryService dictionaryService;

    private final BookRepository bookRepository;
    private final BookUserRepository bookUserRepository;
    private final UserRepository userRepository;

    private final EntityManager entityManager;

    /**
     * Save a book.
     *
     * @param bookDTO the entity to save.
     * @return the persisted entity.
     */
    public BookDTO save(BookDTO bookDTO) {
        log.debug("Request to save Book : {}", bookDTO);
        Book book = bookMapper.toEntity(bookDTO);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        if (book.getLoadedUser() == null) {
            book.setLoadedUser(user);
        }
        if (book.getUsers() == null) {
            book.setUsers(new HashSet<>());
        }
        book.getUsers().add(user);

        Dictionary dictionary = dictionaryService.createByText(book.getText(), "eng", "ru");
        book.setDictionary(dictionary);

        book = bookRepository.save(book);
        return bookMapper.toDto(book);
    }

    /**
     * Get all the books.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BookDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        return bookRepository.findAll(pageable)
            .map(bookMapper::toDto);
    }


    /**
     * Get all the books with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BookDTO> findAllWithEagerRelationships(Pageable pageable) {
        return bookRepository.findAllWithEagerRelationships(pageable).map(bookMapper::toDto);
    }

    /**
     * Get one book by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BookDTO> findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        return bookRepository.findOneWithEagerRelationships(id)
            .map(bookMapper::toDto);
    }

    /**
     * Delete the book by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.deleteById(id);
    }

    /**
     * Выствляет дату последнего открытия книги на текущую
     * Если данную книгу пользователь открывает впервые, создается новая запись
     *
     * @param id идентификатор книги
     */
    public void updateLastOpenDate(Long id) {
        log.debug("Request to update last open book date: {}", id);

        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        entityManager.merge(user);

        BookUser bookUser = bookUserRepository.findById(new BookUserId(user.getId(), id))
            .orElseGet(() -> new BookUser(user.getId(), id));

        bookUser.setLastOpenDate(LocalDateTime.now());
        bookUserRepository.save(bookUser);
    }
}
