package ru.mrak.service.book;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.repository.BookSentenceRepository;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookSentenceService {

    private final Logger log = LoggerFactory.getLogger(BookSentenceService.class);

    private final BookSentenceRepository bookSentenceRepository;

    /**
     * Возвращает предложения указанной книги
     */
    @Transactional(readOnly = true)
    public List<BookSentence> getSentences(long bookId) {
        log.debug("get all sentence for book, bookId: {}", bookId);
        List<BookSentence> bookSentences = bookSentenceRepository.findAllByBookId(bookId);
        bookSentences.sort(Comparator.comparing(BookSentence::getId));
        return bookSentences;
    }
}
