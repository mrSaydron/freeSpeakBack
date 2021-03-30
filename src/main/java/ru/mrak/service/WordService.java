package ru.mrak.service;

import edu.stanford.nlp.ling.CoreLabel;
import lombok.RequiredArgsConstructor;
import ru.mrak.domain.Book;
import ru.mrak.domain.ServiceData;
import ru.mrak.domain.Word;
import ru.mrak.domain.enumeration.ServiceDataKeysEnum;
import ru.mrak.repository.ServiceDataRepository;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.dto.WordDTO;
import ru.mrak.service.mapper.WordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Word}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class WordService {

    private final Logger log = LoggerFactory.getLogger(WordService.class);

    private final ServiceDataService serviceDataService;

    private final WordRepository wordRepository;

    private final WordMapper wordMapper;

    /**
     * Save a word.
     *
     * @param wordDTO the entity to save.
     * @return the persisted entity.
     */
    public WordDTO save(WordDTO wordDTO) {
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
    public Page<WordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Words");
        return wordRepository.findAll(pageable)
            .map(wordMapper::toDto);
    }


    /**
     * Get one word by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WordDTO> findOne(Long id) {
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
     * @param token - токен
     * @return - созданное слово
     */
    public Word create(CoreLabel token) {
        Word word = new Word();
        word.setWord(token.lemma());
        word.setPartOfSpeech(token.tag());

        wordRepository.save(word);

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
    public void updateTotalAmount(Book book) {
        wordRepository.updateTotalAmountByBookId(book.getId());
        long wordsCount = wordRepository.getWordsCountByBook(book.getId());
        wordsCount += Long.parseLong(serviceDataService.getByKey(ServiceDataKeysEnum.totalWords).getValue());
        serviceDataService.save(ServiceDataKeysEnum.totalWords, String.valueOf(wordsCount));
    }
}
