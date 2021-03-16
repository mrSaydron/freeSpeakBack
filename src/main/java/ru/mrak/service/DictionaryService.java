package ru.mrak.service;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import jdk.nashorn.internal.ir.annotations.Reference;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import ru.mrak.domain.Dictionary;
import ru.mrak.domain.DictionaryHasWord;
import ru.mrak.domain.Word;
import ru.mrak.domain.enumeration.TagEnum;
import ru.mrak.repository.DictionaryHasWordRepository;
import ru.mrak.repository.DictionaryRepository;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.dto.DictionaryDTO;
import ru.mrak.service.mapper.DictionaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Dictionary}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DictionaryService {

    private final Logger log = LoggerFactory.getLogger(DictionaryService.class);

    private final TokenizerService tokenizerService;
    private final WordService wordService;

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryHasWordRepository dictionaryHasWordRepository;
    private final WordRepository wordRepository;

    private final DictionaryMapper dictionaryMapper;

    /**
     * Save a dictionary.
     *
     * @param dictionaryDTO the entity to save.
     * @return the persisted entity.
     */
    public DictionaryDTO save(DictionaryDTO dictionaryDTO) {
        log.debug("Request to save Dictionary : {}", dictionaryDTO);
        Dictionary dictionary = dictionaryMapper.toEntity(dictionaryDTO);
        dictionary = dictionaryRepository.save(dictionary);
        return dictionaryMapper.toDto(dictionary);
    }

    /**
     * Get all the dictionaries.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DictionaryDTO> findAll() {
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
    public Optional<DictionaryDTO> findOne(Long id) {
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
    public Dictionary createByText(String text, String baseLanguage, String targerLanguage) {
        Dictionary dictionary = new Dictionary();
        dictionary.setBaseLanguage(baseLanguage);
        dictionary.setTargerLanguage(targerLanguage);

        List<DictionaryHasWord> dictionaryWords = new ArrayList<>();
        dictionary.setDictionaryWords(dictionaryWords);

        try {
            List<CoreLabel> tokens = tokenizerService.getTokens(text);
            List<DictionaryHasWord> dictionaryHasWords = tokens.stream()
                .filter(token -> TagEnum.filterTags.contains(TagEnum.byTag.get(token.tag())))
                .map(TokenLight::new)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .map(entry -> {
                    CoreLabel token = entry.getKey().token;
                    Long count = entry.getValue();

                    Optional<Word> optionalWord = wordRepository.findByWord(token.lemma());
                    Word word = optionalWord.orElseGet(() -> wordService.create(token));

                    DictionaryHasWord dictionaryWord = new DictionaryHasWord();
                    dictionaryWord.setCount(count.intValue());
                    dictionaryWord.setWord(word);
                    dictionaryWord.setDictionary(dictionary);

                    return dictionaryWord;
                })
                .collect(Collectors.toList());

            wordRepository.flush();
            dictionaryHasWordRepository.saveAll(dictionaryHasWords);

        } catch (Exception e) {
            System.out.println(e);
        }

        dictionaryRepository.save(dictionary);
        return dictionary;
    }

    private static class TokenLight {
        String lemma;
        String tag;
        CoreLabel token;

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
