package ru.mrak.controller;

import io.github.jhipster.web.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mrak.model.entity.userWordProgress.UserWord;
import ru.mrak.model.enumeration.UserWordProgressTypeEnum;
import ru.mrak.service.UserWordService;
import ru.mrak.dto.userWord.UserWordCriteria;
import ru.mrak.dto.userWord.UserWordDto;
import ru.mrak.mapper.UserWordMapper;

import java.util.List;

/**
 * Контроллер для работы со словарем пользователя
 */
@RestController
@RequestMapping("/api/user-word")
@RequiredArgsConstructor
public class UserWordController {

    private final Logger log = LoggerFactory.getLogger(UserWordController.class);

    private final UserWordService userWordService;

    private final UserWordMapper userWordMapper;

    /**
     * {@code GET /user-words} : get words for user
     */
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserWordDto>> getUserWords(UserWordCriteria criteria, Pageable pageable) {
        log.debug("REST request to get words for user by criteria: {}", criteria);
        Page<UserWord> userWords = userWordService.findByCriteria(criteria, pageable);
        Page<UserWordDto> page = userWords.map(userWordMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{wordId}")
    @Transactional(readOnly = true)
    public UserWordDto get(@PathVariable long wordId) {
        log.debug("REST request user word by word id: {}", wordId);
        UserWord userWord = userWordService.get(wordId);
        return userWordMapper.toDto(userWord);
    }

    /**
     * Добавляет слово в словарь пользователя
     */
    @PutMapping("/add-word/{wordId}")
    public void addWord(@PathVariable Long wordId) {
        log.debug("REST request to add word in user dictionary. Word id: {}", wordId);
        userWordService.addOrUpdateWord(wordId);
    }

    /**
     * Удаляет слово из словаря пользователя
     */
    @PutMapping("/remove-word/{wordId}")
    public void removeWord(@PathVariable Long wordId) {
        log.debug("REST request to remove word from user dictionary. Word id: {}", wordId);
        userWordService.removeWord(wordId);
    }

    /**
     * Удаляет слова из словаря пользователя
     */
    @PutMapping("/remove-words")
    public void removeWords(@RequestBody List<Long> wordIds) {
        log.debug("REST request to remove words from user dictionary. Word ids: {}", wordIds);
        userWordService.removeWords(wordIds);
    }

    /**
     * Удаляет слова из словаря пользователя по запросу
     */
    @PutMapping("/remove-all-words")
    public void removeAllWords(UserWordCriteria criteria) {
        log.debug("REST request to remove words from user dictionary by criteria. Criteria: {}", criteria);
        userWordService.removeAllWords(criteria);
    }

    /**
     * Сбрасывает прогресс слова
     */
    @PutMapping("/erase-word/{wordId}")
    public void eraseWord(@PathVariable Long wordId) {
        log.debug("REST request to erase progress word from user dictionary. Word id: {}", wordId);
        userWordService.eraseWord(wordId);
    }

    /**
     * Сбрасывает прогресс слов
     */
    @PutMapping("/erase-words")
    public void eraseWords(@RequestBody List<Long> wordIds) {
        log.debug("REST request to remove erase progress words from user dictionary. Word ids: {}", wordIds);
        userWordService.eraseWords(wordIds);
    }

    /**
     * Сбрасывает прогресс слов по запросу
     */
    @PutMapping("/erase-all-words")
    public void eraseAllWords(UserWordCriteria criteria) {
        log.debug("REST request to remove words from user dictionary by criteria. Criteria: {}", criteria);
        userWordService.eraseAllWords(criteria);
    }

    /**
     * Отмечает слово выученным
     */
    @PutMapping("/know-word/{wordId}")
    public void knowWord(@PathVariable Long wordId) {
        log.debug("REST request to move word to know box. Word id: {}", wordId);
        userWordService.knowWord(wordId, false);
    }

    /**
     * Отмечает слова выученными
     */
    @PutMapping("/know-words")
    public void knowWords(@RequestBody List<Long> wordIds) {
        log.debug("REST request to move words to know box. Word ids: {}", wordIds);
        userWordService.knowWords(wordIds);
    }

    /**
     * Отмечает слова выученнвми по запросу
     */
    @PutMapping("/know-all-words")
    public void knowAllWords(UserWordCriteria criteria) {
        log.debug("REST request to nome words to know box by criteria. Criteria: {}", criteria);
        userWordService.knowAllWords(criteria);
    }

    /**
     * Возвращает осташиеся жизни пользователя для текущего дня
     */
    @GetMapping("/left-hearts")
    public Integer getHearts() {
        log.debug("REST request to hearts user left");
        return userWordService.getLeftHearts();
    }

    /**
     * Возвращает оставшиеся слова для изучения в этот день
     */
    @GetMapping("/words-of-day")
    @Transactional(readOnly = true)
    public List<UserWordDto> getWordsOfDay() {
        log.debug("REST request words of day");
        List<UserWord> userWords = userWordService.getWordsOfDay();
        return userWordMapper.toDto(userWords);
    }

    /**
     * Возвращает допольнительные слова для изучения из словаря пользователя
     * @param excludeWordIds исключает слова из выдачи
     */
    @GetMapping("/next-words")
    @Transactional(readOnly = true)
    public List<UserWordDto> getNextWords(@RequestParam(required = false, name = "exclude-word-ids") List<Long> excludeWordIds) {
        log.debug("GET next words");
        List<UserWord> userWords = userWordService.getNextWords(excludeWordIds);
        return userWordMapper.toDto(userWords);
    }

    /**
     * Пользователь ответил на слово не верно
     */
    @PutMapping("/answer-fail")
    public void answerFail(
        @RequestParam Long userWordId,
        @RequestParam UserWordProgressTypeEnum type
    ) {
        log.debug("REST request word fail, word id: {}, type: {}", userWordId, type);
        userWordService.answerFail(userWordId, type);
    }

    /**
     * Пользователь ответил верно
     */
    @PutMapping("/answer-success")
    public void answerSuccess(
        @RequestParam Long userWordId,
        @RequestParam UserWordProgressTypeEnum type
    ) {
        log.debug("REST request word success, word id: {}, type: {}", userWordId, type);
        userWordService.answerSuccess(userWordId, type);
    }
}
