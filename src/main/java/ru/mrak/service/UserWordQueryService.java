package ru.mrak.service;

import io.github.jhipster.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.domain.*;
import ru.mrak.repository.UserDictionaryHasWordRepository;
import ru.mrak.service.dto.userWord.UserWordCriteria;

import javax.persistence.criteria.JoinType;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWordQueryService extends QueryService<UserDictionaryHasWord> {

    private final Logger log = LoggerFactory.getLogger(UserWordQueryService.class);

    private final UserService userService;

    private final UserDictionaryHasWordRepository userDictionaryHasWordRepository;

    public Specification<UserDictionaryHasWord> createSpecification(UserWordCriteria criteria, User user) {
        log.debug("create specification, criteria: {}, user: {}", criteria, user);
        Specification<UserDictionaryHasWord> specification = (root, query, builder) -> builder.equal(root.join(UserDictionaryHasWord_.dictionary, JoinType.INNER).join(UserDictionary_.user).get(User_.ID), user.getId());

        if (criteria != null) {
            // Фильтры
            if (criteria.getWordFilter() != null && criteria.getWordFilter().getContains() != null) {
                Specification<UserDictionaryHasWord> wordContains
                    = (root, query, builder) -> builder.like(root.join(UserDictionaryHasWord_.word, JoinType.INNER).get(Word_.word), "%" + criteria.getWordFilter().getContains() + "%");
                specification = specification.and(wordContains);
            }
            if (criteria.getPartOfSpeech() != null && criteria.getPartOfSpeech().getContains() != null) {
                Specification<UserDictionaryHasWord> partOfSpeechContains
                    = (root, query, builder) -> builder.like(root.join(UserDictionaryHasWord_.word, JoinType.INNER).get(Word_.partOfSpeech), criteria.getPartOfSpeech().getContains());
                specification = specification.and(partOfSpeechContains);
            }

            // Сортировки
            if (criteria.getStartWord() != null && criteria.getStartWord().getGreaterThan() != null) {
                Specification<UserDictionaryHasWord> wordSort
                    = (root, query, builder) -> builder.greaterThan(root.join(UserDictionaryHasWord_.word, JoinType.INNER).get(Word_.word), criteria.getStartWord().getGreaterThan());
                specification = specification.and(wordSort);
            }
            if (criteria.getStartWord() != null && criteria.getStartWord().getLessThan() != null) {
                Specification<UserDictionaryHasWord> wordSort
                    = (root, query, builder) -> builder.lessThan(root.join(UserDictionaryHasWord_.word, JoinType.INNER).get(Word_.word), criteria.getStartWord().getGreaterThan());
                specification = specification.and(wordSort);
            }
            if (criteria.getStartPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartPriority(), UserDictionaryHasWord_.priority));
            }
        }

        return specification;
    }
}
