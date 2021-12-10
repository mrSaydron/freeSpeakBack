package ru.mrak.service;

import io.github.jhipster.service.filter.Filter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.TokenLight;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;
import ru.mrak.model.enumeration.ServiceDataKeysEnum;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.dto.WordCriteria;
import ru.mrak.service.dto.WordDto;
import ru.mrak.service.mapper.WordMapper;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Word}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class WordService {

    private final Logger log = LoggerFactory.getLogger(WordService.class);

    private final WordMapper wordMapper;

    private final ServiceDataService serviceDataService;
    private final UserService userService;
    private final WordQueryService wordQueryService;

    private final WordRepository wordRepository;

    /**
     * Save a word.
     *
     * @param wordDTO the entity to save.
     * @return the persisted entity.
     */
    public WordDto save(WordDto wordDTO) {
        log.debug("Request to save Word : {}", wordDTO);
        Word word = wordMapper.toEntity(wordDTO);
        word = wordRepository.save(word);
        return wordMapper.toDto(word);
    }

    /**
     * Get all the words.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WordDto> findAll(Pageable pageable) {
        log.debug("Request to get all Words");
        return wordRepository.findAll(pageable)
            .map(wordMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<Word> findByCriteriaForUser(WordCriteria criteria, Pageable page) {
        log.debug("Request to get all Words for user");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        Filter<Long> userFilter = new Filter<>();
        userFilter.setEquals(user.getId());
        criteria.setUser(userFilter);

        Specification<Word> specification = wordQueryService.createSpecification(criteria);
        return wordRepository.findAll(specification, page);
    }

    /**
     * Get one word by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WordDto> findOne(Long id) {
        log.debug("Request to get Word : {}", id);
        return wordRepository.findById(id)
            .map(wordMapper::toDto);
    }

    /**
     * Delete the word by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Word : {}", id);
        wordRepository.deleteById(id);
    }

    /**
     * Создается слово из токена
     * @return - созданное слово
     */
    public Word create(TokenLight tokenLight) {
        Word word = new Word();
        word.setWord(tokenLight.getWord());
        word.setPartOfSpeech(tokenLight.getTag());
        word.setTotalAmount(0L);
        wordRepository.save(word);
        wordRepository.flush();

        return word;
    }

    /**
     * Обновляет количество слов (частоту) по всем публичным книгам
     */
    public void updateTotalAmount() {
        wordRepository.updateTotalAmount();
        Long wordsCount = wordRepository.getWordsCount();
        serviceDataService.save(ServiceDataKeysEnum.totalWords, wordsCount.toString());
    }

    /**
     * Обновляет количество слов (частоту) по книге
     */
    public void updateTotalAmount(long bookId) {
        wordRepository.updateTotalAmountByBookId(bookId);
        long wordsCount = wordRepository.getWordsCountByBook(bookId);
        wordsCount += Long.parseLong(serviceDataService.getByKey(ServiceDataKeysEnum.totalWords).getValue());
        serviceDataService.save(ServiceDataKeysEnum.totalWords, String.valueOf(wordsCount));
    }
}
