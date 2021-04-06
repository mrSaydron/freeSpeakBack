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
import ru.mrak.domain.UserDictionaryHasWord;
import ru.mrak.service.UserWordQueryService;
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
public class UserWordResource {

    private final Logger log = LoggerFactory.getLogger(UserWordResource.class);

    private final UserWordQueryService userWordQueryService;
    private final UserWordService userWordService;

    private final UserWordMapper userWordMapper;

    /**
     * {@code GET /user-words} : get words for user
     */
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserWordDTO>> getUserWords(UserWordCriteria criteria, Pageable pageable) {
        log.debug("REST request to get words for user by criteria: {}", criteria);
        Page<UserDictionaryHasWord> pageHasWord = userWordQueryService.findByCriteria(criteria, pageable);
        Page<UserWordDTO> page = pageHasWord.map(userWordMapper::toDto);
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
}
