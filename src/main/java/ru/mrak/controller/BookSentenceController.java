package ru.mrak.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.service.UserMarkedBookService;
import ru.mrak.service.UserSentencesService;
import ru.mrak.service.book.BookSentenceService;
import ru.mrak.dto.BookSentenceDTO;
import ru.mrak.dto.BookSentenceReadDto;
import ru.mrak.mapper.BookSentenceMapper;
import ru.mrak.mapper.BookSentenceReadMapper;

import java.util.List;

@RestController
@RequestMapping("/api/book/sentence")
@RequiredArgsConstructor
public class BookSentenceController {

    private final Logger log = LoggerFactory.getLogger(BookSentenceController.class);

    private final BookSentenceReadMapper bookSentenceReadMapper;
    private final BookSentenceMapper bookSentenceMapper;

    private final BookSentenceService bookSentenceService;
    private final UserSentencesService userSentencesService;
    private final UserMarkedBookService userMarkedBookService;

    /**
     * Возвращает предложения для урока "тренировака предложений"
     */
    @GetMapping("/read")
    @Transactional
    public List<BookSentenceReadDto> getSentenceForUser() {
        log.debug("Get sentence for user");
        List<BookSentence> sentenceForUser = userSentencesService.getMarkedSentences();
        return bookSentenceReadMapper.toDto(sentenceForUser);
    }

    /**
     * Помечает предложение прочитанным
     */
    @PutMapping("/read/successful/{bookSentenceId}")
    public void successTranslate(@PathVariable long bookSentenceId) {
        log.debug("User success translate sentence: id {}", bookSentenceId);
        userSentencesService.successTranslate(bookSentenceId);
    }

    /**
     * Возвращает все предложения для книги
     */
    @GetMapping
    @Transactional(readOnly = true)
    public List<BookSentenceDTO> getSentences(@RequestParam Long bookId) {
        log.debug("REST all sentences in book, book id: {}", bookId);
        List<BookSentence> bookServices = bookSentenceService.getSentences(bookId);
        return bookSentenceMapper.toDto(bookServices);
    }

    /**
     * Возвращает предложения для урока "чтение отмеченной книги"
     */
    @GetMapping("/read-marked-book")
    @Transactional(readOnly = true)
    public List<BookSentenceReadDto> getSentencesFromMarkedBook() {
        log.debug("GET sentences from book to read");
        List<BookSentence> sentenceToLearn = userMarkedBookService.getSentencesFromMarkedBook();
        return bookSentenceReadMapper.toDto(sentenceToLearn);
    }

    /**
     * Отмечает предложение прочитанным в уроку "чтение отмеченной книги"
     */
    @PutMapping("/read-marked-book/successful/{bookSentenceId}")
    public void successTranslateMarkedBook(@PathVariable long bookSentenceId) {
        log.debug("PUT User success translate sentence from marked book: id {}", bookSentenceId);
        userMarkedBookService.successTranslateFromMarkedBook(bookSentenceId);
    }
}
