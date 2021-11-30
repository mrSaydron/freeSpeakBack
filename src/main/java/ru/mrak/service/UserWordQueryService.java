package ru.mrak.service;

import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.RangeFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word_;
import ru.mrak.model.entity.userWordProgress.UserWord;
import ru.mrak.model.entity.userWordProgress.UserWordHasProgress;
import ru.mrak.model.entity.userWordProgress.UserWordHasProgress_;
import ru.mrak.model.entity.userWordProgress.UserWord_;
import ru.mrak.service.dto.userWord.UserWordCriteria;
import ru.mrak.web.rest.filter.StringRangeFilter;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWordQueryService extends QueryService<UserWord> {

    private final Logger log = LoggerFactory.getLogger(UserWordQueryService.class);

    public Specification<UserWord> createSpecification(UserWordCriteria criteria, User user) {
        log.debug("create specification, criteria: {}, user: {}", criteria, user);
        Specification<UserWord> specification = (root, query, builder) -> builder.equal(root.get(UserWord_.user), user);
        if (criteria != null) {
            if (criteria.getWord() != null) {
                Specification<UserWord> word = buildReferringEntitySpecification(
                    criteria.getWord(),
                    UserWord_.word,
                    Word_.word);
                specification = specification.and(word);
            }
            if (criteria.getWordOrTranslate() != null && criteria.getWordOrTranslate().getContains() != null) {
                Specification<UserWord> wordSpecification = buildReferringEntitySpecification(
                    criteria.getWordOrTranslate(),
                    UserWord_.word,
                    Word_.word);
                Specification<UserWord> translateSpecification = buildReferringEntitySpecification(
                    criteria.getWordOrTranslate(),
                    UserWord_.word,
                    Word_.translate);
                specification = specification.and(wordSpecification.or(translateSpecification));
            }
            if (criteria.getBoxNumber() != null && criteria.getBoxNumber().getEquals() != null) {
                // todo джойнится прогресс, если типов будет много, то будут дубли
                Specification<UserWord> boxNumberEquals
                    = (root, query, builder) -> builder.equal(root.join(UserWord_.wordProgresses, JoinType.INNER).get(UserWordHasProgress_.BOX_NUMBER), criteria.getBoxNumber().getEquals());
                specification = specification.and(boxNumberEquals);
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPriority(), UserWord_.priority));
            }
        }

        return specification;
    }

    private <OTHER> Specification<UserWord> buildReferringEntitySpecification(StringRangeFilter filter,
                                                                              SingularAttribute<UserWord, OTHER> reference,
                                                                              SingularAttribute<? super OTHER, String> valueField) {
        Function<Root<UserWord>, Expression<String>> metaclassFunction = root -> root.join(reference).get(valueField);
        if (filter.getEquals() != null) {
            return equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(metaclassFunction, filter.getIn());
        }

        Specification<UserWord> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            result = result.and(byFieldSpecified(metaclassFunction, filter.getSpecified()));
        }
        if (filter.getNotEquals() != null) {
            result = result.and(notEqualsSpecification(metaclassFunction, filter.getNotEquals()));
        }
        if (filter.getNotIn() != null) {
            result = result.and(valueNotIn(metaclassFunction, filter.getNotIn()));
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(greaterThan(metaclassFunction, filter.getGreaterThan()));
        }
        if (filter.getGreaterThanOrEqual() != null) {
            result = result.and(greaterThanOrEqualTo(metaclassFunction, filter.getGreaterThanOrEqual()));
        }
        if (filter.getLessThan() != null) {
            result = result.and(lessThan(metaclassFunction, filter.getLessThan()));
        }
        if (filter.getLessThanOrEqual() != null) {
            result = result.and(lessThanOrEqualTo(metaclassFunction, filter.getLessThanOrEqual()));
        }
        if (filter.getContains() != null) {
            result = result.and(likeUpperSpecification(metaclassFunction, filter.getContains()));
        }
        if (filter.getDoesNotContain() != null) {
            result = result.and(doesNotContainSpecification(metaclassFunction, filter.getDoesNotContain()));
        }
        return result;
    }
}
