package ru.mrak.service;

import edu.stanford.nlp.ling.CoreLabel;
import lombok.RequiredArgsConstructor;
import ru.mrak.domain.BookDictionary;
import ru.mrak.domain.BookDictionaryHasWord;
import ru.mrak.domain.Word;
import ru.mrak.domain.enumeration.TagEnum;
import ru.mrak.repository.DictionaryHasWordRepository;
import ru.mrak.repository.DictionaryRepository;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.dto.BookDictionaryDTO;
import ru.mrak.service.mapper.DictionaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.service.tarnslate.TranslateService;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BookDictionary}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DictionaryService {

    private final Logger log = LoggerFactory.getLogger(DictionaryService.class);

    private final DictionaryMapper dictionaryMapper;

    private final TokenizerService tokenizerService;
    private final WordService wordService;
    private final TranslateService translateService;

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryHasWordRepository dictionaryHasWordRepository;
    private final WordRepository wordRepository;

    private final EntityManager entityManager;

    /**
     * Save a dictionary.
     *
     * @param dictionaryDTO the entity to save.
     * @return the persisted entity.
     */
    public BookDictionaryDTO save(BookDictionaryDTO dictionaryDTO) {
        log.debug("Request to save Dictionary : {}", dictionaryDTO);
        BookDictionary dictionary = dictionaryMapper.toEntity(dictionaryDTO);
        dictionary = dictionaryRepository.save(dictionary);
        return dictionaryMapper.toDto(dictionary);
    }

    /**
     * Get all the dictionaries.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BookDictionaryDTO> findAll() {
        log.debug("Request to get all Dictionaries");
        return dictionaryRepository.findAll().stream()
            .map(dictionaryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dictionary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BookDictionaryDTO> findOne(Long id) {
        log.debug("Request to get Dictionary : {}", id);
        return dictionaryRepository.findById(id)
            .map(dictionaryMapper::toDto);
    }

    /**
     * Delete the dictionary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Dictionary : {}", id);
        dictionaryRepository.deleteById(id);
    }

    /**
     * Формирует словарь из переданного текста. Текст разбивается на токены, фильтруется, считается количество слов
     * @param text - текст из которого формируется словарь
     * @param baseLanguage - язык текста
     * @param targerLanguage - целеваой язык
     * @return - словарь
     */
    public BookDictionary createByText(String text, String baseLanguage, String targerLanguage) {
        BookDictionary dictionary = new BookDictionary();
        dictionary.setBaseLanguage(baseLanguage);
        dictionary.setTargetLanguage(targerLanguage);

        List<BookDictionaryHasWord> dictionaryWords = new ArrayList<>();
        dictionary.setDictionaryWords(dictionaryWords);

        try {
            List<CoreLabel> tokens = tokenizerService.getTokens(text);
            Map<TokenLight, Long> tokenCountMap = tokens.stream()
                .filter(token -> TagEnum.filterTags.contains(TagEnum.byTag.get(token.tag())))
                .map(TokenLight::new)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            List<Word> newWords = new ArrayList<>();
            tokenCountMap.forEach((tokenLight, count) -> {
                Optional<Word> optionalWord = wordRepository.findByWordAndPartOfSpeech(tokenLight.token.lemma(), tokenLight.token.tag());
                Word word = optionalWord.orElseGet(() -> {
                    Word newWord = wordService.create(tokenLight.token);
                    newWords.add(newWord);
                    return newWord;
                });

                BookDictionaryHasWord dictionaryWord = new BookDictionaryHasWord();
                dictionaryWord.setCount(count.intValue());
                dictionaryWord.setWord(word);
                dictionaryWord.setDictionary(dictionary);
                dictionary.getDictionaryWords().add(dictionaryWord);
            });

            wordRepository.flush();
            dictionaryRepository.save(dictionary);
            dictionaryHasWordRepository.saveAll(dictionary.getDictionaryWords());
            entityManager.flush();

            translateService.updateWords(newWords);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.debug("CreateByText finish");
        return dictionary;
    }

    public static class TokenLight {
        public String lemma;
        public String tag;
        public CoreLabel token;

        public TokenLight(CoreLabel token) {
            this.token = token;
            this.lemma = token.lemma();
            this.tag = token.tag();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TokenLight that = (TokenLight) o;
            return Objects.equals(lemma, that.lemma) &&
                Objects.equals(tag, that.tag);
        }

        @Override
        public int hashCode() {
            return Objects.hash(lemma, tag);
        }
    }
}
