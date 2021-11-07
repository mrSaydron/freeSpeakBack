package ru.mrak.web.rest;

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
import ru.mrak.service.dto.userWord.UserWordCriteria;
import ru.mrak.service.dto.userWord.UserWordDTO;
import ru.mrak.service.mapper.UserWordMapper;

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
    public ResponseEntity<List<UserWordDTO>> getUserWords(UserWordCriteria criteria, Pageable pageable) {
        log.debug("REST request to get words for user by criteria: {}", criteria);
        Page<UserWord> userWords = userWordService.findByCriteria(criteria, pageable);
        Page<UserWordDTO> page = userWords.map(userWordMapper::toDto);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * Добавляет слово в словарь пользователя
     */
    @PutMapping("/add-word/{wordId}")
    public void addWord(@PathVariable Long wordId) {
        log.debug("REST request to add word in user dictionary. Word id: {}", wordId);
        userWordService.addWord(wordId);
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
     * Удаляет слово из словаря пользователя
     */
    @PutMapping("/erase-word/{wordId}")
    public void eraseWord(@PathVariable Long wordId) {
        log.debug("REST request to erase progress word from user dictionary. Word id: {}", wordId);
        userWordService.eraseWord(wordId);
    }

    /**
     * Удаляет слова из словаря пользователя
     */
    @PutMapping("/erase-words")
    public void eraseWords(@RequestBody List<Long> wordIds) {
        log.debug("REST request to remove erase progress words from user dictionary. Word ids: {}", wordIds);
        userWordService.eraseWords(wordIds);
    }

    /**
     * Удаляет слова из словаря пользователя по запросу
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
        userWordService.knowWord(wordId);
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
    public List<UserWordDTO> getWordsOfDay() {
        log.debug("REST request words of day");
        List<UserWord> userWords = userWordService.getWordsOfDay();
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
