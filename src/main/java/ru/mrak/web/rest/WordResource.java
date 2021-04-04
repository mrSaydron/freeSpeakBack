package ru.mrak.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mrak.domain.Word;
import ru.mrak.service.WordQueryService;
import ru.mrak.service.WordService;
import ru.mrak.service.dto.WordCriteria;
import ru.mrak.service.dto.WordDTO;
import ru.mrak.service.dto.userWord.WordUserJoinDTO;
import ru.mrak.service.mapper.WordMapper;
import ru.mrak.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ru.mrak.domain.Word}.
 */
@RestController
@RequestMapping("/api/word")
@RequiredArgsConstructor
public class WordResource {

    private final Logger log = LoggerFactory.getLogger(WordResource.class);

    private static final String ENTITY_NAME = "word";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WordMapper wordMapper;

    private final WordService wordService;
    private final WordQueryService wordQueryService;

    /**
     * {@code POST  /words} : Create a new word.
     *
     * @param wordDTO the wordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wordDTO, or with status {@code 400 (Bad Request)} if the word has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping
    public ResponseEntity<WordDTO> createWord(@Valid @RequestBody WordDTO wordDTO) throws URISyntaxException {
        log.debug("REST request to save Word : {}", wordDTO);
        if (wordDTO.getId() != null) {
            throw new BadRequestAlertException("A new word cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WordDTO result = wordService.save(wordDTO);
        return ResponseEntity.created(new URI("/api/words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /words} : Updates an existing word.
     *
     * @param wordDTO the wordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wordDTO,
     * or with status {@code 400 (Bad Request)} if the wordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping
    public ResponseEntity<WordDTO> updateWord(@Valid @RequestBody WordDTO wordDTO) throws URISyntaxException {
        log.debug("REST request to update Word : {}", wordDTO);
        if (wordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WordDTO result = wordService.save(wordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wordDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /words} : get all the words.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of words in body.
     */
    @GetMapping
    public ResponseEntity<List<WordDTO>> getAllWordsForUser(WordCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Words by criteria: {}", criteria);
        Page<WordUserJoinDTO> page = wordService.findByCriteriaForUser(criteria, pageable);
        Page<WordDTO> result = page.map(dto -> wordMapper.toDto(dto.getWord(), dto.getUserHas()));

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(result.getContent());
    }

    /**
     * {@code GET  /words/count} : count all the words.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countWords(WordCriteria criteria) {
        log.debug("REST request to count Words by criteria: {}", criteria);
        return ResponseEntity.ok().body(wordQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /words/:id} : get the "id" word.
     *
     * @param id the id of the wordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WordDTO> getWord(@PathVariable Long id) {
        log.debug("REST request to get Word : {}", id);
        Optional<WordDTO> wordDTO = wordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wordDTO);
    }

    /**
     * {@code DELETE  /words/:id} : delete the "id" word.
     *
     * @param id the id of the wordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Long id) {
        log.debug("REST request to delete Word : {}", id);
        wordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * Пересчитывает частоту слов по всем публичным книгам
     */
    @PutMapping("/update-words-total-amount")
    public ResponseEntity<Void> updateWordsTotalAmount() {
        log.debug("REST update words total amount");
        wordService.updateTotalAmount();
        return ResponseEntity.ok().build();

    }
}
