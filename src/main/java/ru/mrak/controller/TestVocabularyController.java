package ru.mrak.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mrak.service.TestVocabularyService;
import ru.mrak.dto.testVocabulary.TestVocabularyDto;

/**
 * Контроллер для определения словарного запаса пользователя
 */
@RestController
@RequestMapping("/api/test-vocabulary")
@RequiredArgsConstructor
public class TestVocabularyController {

    private final Logger log = LoggerFactory.getLogger(TestVocabularyController.class);

    private final TestVocabularyService testVocabularyService;

    @PostMapping("/start")
    public TestVocabularyDto testStart() {
        log.debug("POST test start");
        return testVocabularyService.start();
    }

    @PostMapping("/answer-fail")
    public TestVocabularyDto answerFail(@RequestParam long wordId, @RequestParam long testVocabularyId) {
        log.debug("POST next word, id: {}", wordId);
        return testVocabularyService.answerFail(wordId, testVocabularyId);
    }

    @PostMapping("/answer-success")
    public TestVocabularyDto answerSuccess(@RequestParam long wordId, @RequestParam long testVocabularyId) {
        log.debug("POST next word, id: {}", wordId);
        return testVocabularyService.answerSuccess(wordId, testVocabularyId);
    }
}
