package ru.mrak.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import ru.mrak.domain.Word;
import ru.mrak.domain.*; // for static metamodels
import ru.mrak.repository.WordRepository;
import ru.mrak.service.dto.WordCriteria;
import ru.mrak.service.dto.WordDTO;
import ru.mrak.service.mapper.WordMapper;

/**
 * Service for executing complex queries for {@link Word} entities in the database.
 * The main input is a {@link WordCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WordDTO} or a {@link Page} of {@link WordDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WordQueryService extends QueryService<Word> {

    private final Logger log = LoggerFactory.getLogger(WordQueryService.class);

    private final WordRepository wordRepository;

    private final WordMapper wordMapper;

    public WordQueryService(WordRepository wordRepository, WordMapper wordMapper) {
        this.wordRepository = wordRepository;
        this.wordMapper = wordMapper;
    }

    /**
     * Return a {@link List} of {@link WordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WordDTO> findByCriteria(WordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Word> specification = createSpecification(criteria);
        return wordMapper.toDto(wordRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Word> findByCriteria(WordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Word> specification = createSpecification(criteria);
        return wordRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Word> specification = createSpecification(criteria);
        return wordRepository.count(specification);
    }

    /**
     * Function to convert {@link WordCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Word> createSpecification(WordCriteria criteria) {
        Specification<Word> specification = Specification.where(null);
        if (criteria != null) {
            // Филтры
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Word_.id));
            }
            if (criteria.getPartOfSpeechFilter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartOfSpeechFilter(), Word_.partOfSpeech));
            }
            if (criteria.getWordFilter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWordFilter(), Word_.word));
            }

            // Сортировки
            if (criteria.getStartWord() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartWord(), Word_.word));
            }
            if (criteria.getStartAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartAmount(), Word_.totalAmount));
            }

        }
        return specification;
    }
}
