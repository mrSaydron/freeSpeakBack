package ru.mrak.web.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.service.BookSentenceService;
import ru.mrak.service.dto.BookSentenceDTO;
import ru.mrak.service.dto.BookSentenceReadDto;
import ru.mrak.service.mapper.BookSentenceMapper;
import ru.mrak.service.mapper.BookSentenceReadMapper;

import java.util.List;

@RestController
@RequestMapping("/api/book/sentence")
@RequiredArgsConstructor
public class BookSentenceController {

    private final Logger log = LoggerFactory.getLogger(BookSentenceController.class);

    private final BookSentenceReadMapper bookSentenceReadMapper;
    private final BookSentenceMapper bookSentenceMapper;

    private final BookSentenceService bookSentenceService;

    @GetMapping("/read")
    @Transactional
    public List<BookSentenceReadDto> getSentenceForUser() {
        log.debug("Get sentence for user");
        List<BookSentence> sentenceForUser = bookSentenceService.getSentenceForUser();
        return bookSentenceReadMapper.toDto(sentenceForUser);
    }

    @PutMapping("/read/successful/{bookSentenceId}")
    public void successTranslate(@PathVariable long bookSentenceId) {
        log.debug("User success translate sentence: id {}", bookSentenceId);
        bookSentenceService.successTranslate(bookSentenceId);
    }

    @PutMapping("/read/fail/{bookSentenceId}")
    public void failTranslate(@PathVariable long bookSentenceId) {
        log.debug("User fail translate sentence: id {}", bookSentenceId);
        bookSentenceService.failTranslate(bookSentenceId);
    }

    @Transactional(readOnly = true)
    @GetMapping
    public List<BookSentenceDTO> getSentences(@RequestParam Long bookId) {
        log.debug("REST all words in book");
        List<BookSentence> bookServices = bookSentenceService.getSentences(bookId);
        return bookSentenceMapper.toDto(bookServices);
    }

}
