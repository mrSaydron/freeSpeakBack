package ru.mrak.service;

import io.github.jhipster.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word_;
import ru.mrak.model.entity.userWordProgress.UserWordProgress;
import ru.mrak.model.entity.userWordProgress.UserWordProgress_;
import ru.mrak.service.dto.userWord.UserWordCriteria;

import javax.persistence.criteria.JoinType;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWordQueryService extends QueryService<UserWordProgress> {

    private final Logger log = LoggerFactory.getLogger(UserWordQueryService.class);

    public Specification<UserWordProgress> createSpecification(UserWordCriteria criteria, User user) {
        log.debug("create specification, criteria: {}, user: {}", criteria, user);

        Specification<UserWordProgress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getWord() != null) {
                Specification<UserWordProgress> word = buildReferringEntitySpecification(
                    criteria.getWord(),
                    UserWordProgress_.word,
                    Word_.word);
                specification = specification.and(word);
            }
            if (criteria.getPartOfSpeech() != null) {
                Specification<UserWordProgress> partOfSpeech = buildReferringEntitySpecification(
                    criteria.getPartOfSpeech(),
                    UserWordProgress_.word,
                    Word_.partOfSpeech);
                specification = specification.and(partOfSpeech);
            }
            if (criteria.getBoxNumber() != null && criteria.getBoxNumber().getEquals() != null) {
                Specification<UserWordProgress> box = buildRangeSpecification(
                    criteria.getBoxNumber(),
                    UserWordProgress_.boxNumber);
                specification = specification.and(box);
            }
        }

        return specification;
    }
}
