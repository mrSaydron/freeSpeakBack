package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.domain.*;
import ru.mrak.repository.BookRepository;
import ru.mrak.repository.BookUserRepository;
import ru.mrak.service.dto.BookDTO;
import ru.mrak.service.mapper.BookMapper;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
    private final WordService wordService;
    private final UserWordService userWordService;

    private final BookRepository bookRepository;
    private final BookUserRepository bookUserRepository;

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

        BookDictionary dictionary = dictionaryService.createByText(book.getText(), "eng", "ru");
        book.setDictionary(dictionary);
        dictionary.setBook(book);

        book = bookRepository.save(book);
        entityManager.flush();

        if (book.getPublicBook()) {
            wordService.updateTotalAmount(book);
        }

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
        entityManager.merge(user);// похоже что это лишнее

        BookUser bookUser = bookUserRepository.findById(new BookUserId(user.getId(), id))
            .orElseGet(() -> new BookUser(user.getId(), id));

        bookUser.setLastOpenDate(LocalDateTime.now());
        bookUserRepository.save(bookUser);
    }

    /**
     * Все ли слова из книги есть в словаре пользователя
     */
    public boolean checkUserLibrary(Long bookId) {
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        return bookRepository.getMissingWords(user.getId(), bookId).isEmpty();
    }

    /**
     * Добавляет недостающие слова из книги в словарь пользователя
     */
    @Transactional
    public void addWordsToDictionary(Long bookId) {
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        List<Long> wordIds = bookRepository.getMissingWords(user.getId(), bookId);
        wordIds.forEach(userWordService::addWord);
    }
}
