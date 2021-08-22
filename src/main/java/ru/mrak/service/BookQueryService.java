package ru.mrak.service;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.domain.*;
import ru.mrak.repository.BookRepository;
import ru.mrak.service.dto.BookCriteria;
import ru.mrak.service.dto.BookDTO;
import ru.mrak.service.mapper.BookMapper;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Service for executing complex queries for {@link Book} entities in the database.
 * The main input is a {@link BookCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BookDTO} or a {@link Page} of {@link BookDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BookQueryService extends QueryService<Book> {

    private final Logger log = LoggerFactory.getLogger(BookQueryService.class);

    private final BookMapper bookMapper;

    private final UserService userService;

    private final BookRepository bookRepository;

    private final EntityManager entityManager;

    public BookQueryService(BookRepository bookRepository, BookMapper bookMapper, UserService userService, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.userService = userService;
        this.entityManager = entityManager;
    }

    /**
     * Return a {@link List} of {@link BookDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BookDTO> findByCriteria(BookCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Book> specification = createSpecification(criteria);
        return bookMapper.toDto(bookRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BookDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BookDTO> findByCriteria(BookCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Book> specification = createSpecification(criteria);
        return bookRepository.findAll(specification, page)
            .map(bookMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BookCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Book> specification = createSpecification(criteria);
        return bookRepository.count(specification);
    }

    /**
     * Function to convert {@link BookCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Book> createSpecification(BookCriteria criteria) {

        Specification<Book> startPredicate;
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("Что то пошло не так"));
        startPredicate = (root, query, builder) -> builder.equal(root.join(Book_.users, JoinType.LEFT).get(User_.ID), user.getId());
        if (criteria != null && criteria.getOrPublicBookFilter() != null && criteria.getOrPublicBookFilter().getEquals()) {
            startPredicate = startPredicate.or(buildSpecification(criteria.getOrPublicBookFilter(), Book_.publicBook));
        }

        Specification<Book> specification = Specification.where(startPredicate);

        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Book_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Book_.title));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthor(), Book_.author));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), Book_.source));
            }
            if (criteria.getText() != null) {
                specification = specification.and(buildStringSpecification(criteria.getText(), Book_.text));
            }
            if (criteria.getPublicBook() != null) {
                specification = specification.and(buildSpecification(criteria.getPublicBook(), Book_.publicBook));
            }
            if (criteria.getDictionaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getDictionaryId(),
                    root -> root.join(Book_.dictionary, JoinType.LEFT).get(BookDictionary_.id)));
            }
            if (criteria.getLoadedUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getLoadedUserId(),
                    root -> root.join(Book_.loadedUser, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Book_.users, JoinType.LEFT).get(User_.id)));
            }

            // Фильтры
            if (criteria.getTitleAuthorFilter() != null) {
                Specification<Book> commonSpecification =
                    Specification.where(buildStringSpecification(criteria.getTitleAuthorFilter(), Book_.author))
                    .or(buildStringSpecification(criteria.getTitleAuthorFilter(), Book_.title));

                specification = specification.and(commonSpecification);
            }

            if (criteria.getKnowFilter() != null) {

                Specification<Book> knowSpecification = (root, query, builder)
                    -> {
                    CollectionJoin<Book, BookUserKnow> userKnowJoin = root.join(Book_.userKnows, JoinType.LEFT);

                    Predicate less;
                    if (criteria.getKnowFilter().getLessThan() != null) {
                        less = builder.lessThan(userKnowJoin.get(BookUserKnow_.know), criteria.getKnowFilter().getLessThan());
                    } else {
                        less = builder.lessThan(userKnowJoin.get(BookUserKnow_.know), criteria.getKnowFilter().getLessThanOrEqual());
                    }
                    Predicate greaterThanOrEqualToKnow = builder.greaterThanOrEqualTo(userKnowJoin.get(BookUserKnow_.know), criteria.getKnowFilter().getGreaterThanOrEqual());
                    Predicate equalUser = builder.equal(userKnowJoin.get(BookUserKnow_.userId), user.getId());

                    return builder.and(less, greaterThanOrEqualToKnow, equalUser);
                };

                specification = specification.and(knowSpecification);
            }

            // Сортировки
            if (criteria.getStartTitle() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTitle(), Book_.title));
            }
            if (criteria.getStartAuthor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartAuthor(), Book_.author));
            }

        }

        return specification;
    }
}
