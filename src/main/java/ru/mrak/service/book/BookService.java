package ru.mrak.service.book;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.mapper.BookCreateMapper;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.model.entity.TextTag;
import ru.mrak.model.entity.User;
import ru.mrak.repository.BookRepository;
import ru.mrak.repository.BookSentenceRepository;
import ru.mrak.service.TextService;
import ru.mrak.service.UserService;
import ru.mrak.service.UserWordService;
import ru.mrak.service.WordService;
import ru.mrak.dto.BookCreateDTO;
import ru.mrak.dto.BookDto;
import ru.mrak.mapper.BookMapper;

import javax.persistence.EntityManager;
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
    private final BookCreateMapper bookCreateMapper;

    private final UserService userService;
    private final WordService wordService;
    private final UserWordService userWordService;
    private final TextService textService;
    private final BookUserService bookUserService;

    private final BookRepository bookRepository;
    private final BookSentenceRepository bookSentenceRepository;

    private final EntityManager entityManager;

    /**
     * Save a book.
     */
    public void save(BookCreateDTO bookDTO) {
        log.debug("Request to save Book : {}", bookDTO);

        Book book = bookCreateMapper.toEntity(bookDTO);
        bookRepository.save(book);

        List<BookSentence> bookSentences = textService.createByText(bookDTO.getText());
        for (BookSentence bookSentence : bookSentences) {
            bookSentence.setBook(book);
            bookSentenceRepository.save(bookSentence);
        }

        entityManager.flush();
        wordService.updateTotalAmount(book.getId());
    }

    /**
     * Get all the books.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BookDto> findAll(Pageable pageable) {
        log.debug("Request to get all Books");
        return bookRepository.findAll(pageable)
            .map(bookMapper::toDto);
    }

    /**
     * Get all the books with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BookDto> findAllWithEagerRelationships(Pageable pageable) {
        return bookRepository.findAllWithEagerRelationships(pageable).map(bookMapper::toDto);
    }

    /**
     * Get one book by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BookDto> findOne(Long id) {
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
     * Все ли слова из книги есть в словаре пользователя
     */
    public boolean checkUserLibrary(Long bookId) {
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        return bookRepository.getMissingWords(user, bookRepository.getOne(bookId)).isEmpty();
    }

    /**
     * Добавляет недостающие слова из книги в словарь пользователя
     */
    public void addWordsToDictionary(long bookId) {
        log.debug("add words to user dictionary in book, book id: {}", bookId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        List<Long> wordIds = bookRepository.getMissingWords(user, bookRepository.getOne(bookId));
        wordIds.forEach(userWordService::addOrUpdateWord);
    }

    /**
     * Помечает книгу "для чтения" и настраивает приоритеты для слов на изучение
     */
    public void setBookIsRead(long bookId) {
        log.debug("set book 'is read' by book id: {}", bookId);
        bookUserService.resetBookIsRead();
        bookUserService.setBookIsRead(bookId);
        userWordService.resetPriority();

        addWordsToDictionary(bookId);
        userWordService.setPriorityByBook(bookId);
    }

    /**
     * Сбрасывает книги "для чтения" для текущего пользователя
     */
    public void resetBookIsRead() {
        log.debug("reset book 'is read'");
        bookUserService.resetBookIsRead();
    }
}
