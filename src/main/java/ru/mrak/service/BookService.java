package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.model.entity.User;
import ru.mrak.repository.BookRepository;
import ru.mrak.repository.BookSentenceRepository;
import ru.mrak.service.dto.BookCreateDTO;
import ru.mrak.service.dto.BookDTO;
import ru.mrak.service.mapper.BookMapper;

import javax.persistence.EntityManager;
import java.util.Comparator;
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
    private final WordService wordService;
    private final UserWordService userWordService;
    private final TextService textService;

    private final BookRepository bookRepository;
    private final BookSentenceRepository bookSentenceRepository;

    private final EntityManager entityManager;

    /**
     * Save a book.
     */
    public void save(BookCreateDTO bookDTO) {
        log.debug("Request to save Book : {}", bookDTO);

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPictureId(bookDTO.getPictureId());
        book = bookRepository.save(book);

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
     * Все ли слова из книги есть в словаре пользователя
     */
    public boolean checkUserLibrary(Long bookId) {
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        return bookRepository.getMissingWords(user, bookRepository.getOne(bookId)).isEmpty();
    }

    /**
     * Добавляет недостающие слова из книги в словарь пользователя
     */
    @Transactional
    public void addWordsToDictionary(Long bookId) {
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        List<Long> wordIds = bookRepository.getMissingWords(user, bookRepository.getOne(bookId));
        wordIds.forEach(userWordService::addWord);
    }

}
