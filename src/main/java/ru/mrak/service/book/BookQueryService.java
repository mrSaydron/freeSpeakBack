package ru.mrak.service.book;

import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.Book_;
import ru.mrak.model.entity.bookUserKnow.BookUserKnow;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.bookUserKnow.BookUserKnow_;
import ru.mrak.repository.BookRepository;
import ru.mrak.service.UserService;
import ru.mrak.dto.BookCriteria;
import ru.mrak.dto.BookDto;
import ru.mrak.mapper.BookMapper;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Service for executing complex queries for {@link Book} entities in the database.
 * The main input is a {@link BookCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BookDto} or a {@link Page} of {@link BookDto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BookQueryService extends QueryService<Book> {

    private final Logger log = LoggerFactory.getLogger(BookQueryService.class);

    private final BookMapper bookMapper;

    private final UserService userService;

    private final BookRepository bookRepository;

    public BookQueryService(BookRepository bookRepository, BookMapper bookMapper, UserService userService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.userService = userService;
    }

    /**
     * Return a {@link List} of {@link BookDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BookDto> findByCriteria(BookCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Book> specification = createSpecification(criteria);
        return bookMapper.toDto(bookRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BookDto} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Book> findByCriteria(BookCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Book> specification = createSpecification(criteria);
        return bookRepository.findAll(specification, page);
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
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("Что то пошло не так"));

        Specification<Book> specification = Specification.where(null);

        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Book_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTitle(), Book_.title));
            }
            if (criteria.getAuthor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAuthor(), Book_.author));
            }
            if (criteria.getTitleAuthor() != null) {
                Specification<Book> commonSpecification =
                    Specification.where(buildStringSpecification(criteria.getTitleAuthor(), Book_.author))
                    .or(buildStringSpecification(criteria.getTitleAuthor(), Book_.title));

                specification = specification.and(commonSpecification);
            }
            if (criteria.getKnow() != null) {
                Specification<Book> knowSpecification = (root, query, builder)
                    -> {
                    CollectionJoin<Book, BookUserKnow> userKnowJoin = root.join(Book_.userKnows, JoinType.LEFT);

                    Predicate less;
                    if (criteria.getKnow().getLessThan() != null) {
                        less = builder.lessThan(userKnowJoin.get(BookUserKnow_.know), criteria.getKnow().getLessThan());
                    } else if (criteria.getKnow().getGreaterThanOrEqual() != null){
                        less = builder.lessThan(userKnowJoin.get(BookUserKnow_.know), criteria.getKnow().getLessThanOrEqual());
                    } else {
                        throw new RuntimeException("Нет такого условия");
                    }
                    Predicate greaterThanOrEqualToKnow
                        = builder.greaterThanOrEqualTo(userKnowJoin.get(BookUserKnow_.know), criteria.getKnow().getGreaterThanOrEqual());
                    Predicate equalUser = builder.equal(userKnowJoin.get(BookUserKnow_.userId), user.getId());

                    return builder.and(less, greaterThanOrEqualToKnow, equalUser);
                };

                specification = specification.and(knowSpecification);
            }
        }

        return specification;
    }
}
