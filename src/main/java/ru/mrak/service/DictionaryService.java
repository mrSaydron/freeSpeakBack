package ru.mrak.service;

import edu.stanford.nlp.ling.CoreLabel;
import lombok.RequiredArgsConstructor;
import ru.mrak.domain.BookDictionary;
import ru.mrak.domain.BookDictionaryHasWord;
import ru.mrak.domain.TokenLight;
import ru.mrak.domain.Word;
import ru.mrak.domain.enumeration.TagEnum;
import ru.mrak.repository.*;
import ru.mrak.service.dto.BookDictionaryDTO;
import ru.mrak.service.mapper.DictionaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.service.tarnslate.TranslateServiceInterface;

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
    private final TranslateServiceInterface translateService;

    private final DictionaryRepository dictionaryRepository;
    private final DictionaryHasWordRepository dictionaryHasWordRepository;
    private final WordRepository wordRepository;
    private final ExceptionWordRepository exceptionWordRepository;
    private final TokenRuleRepository tokenRuleRepository;

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
            .map(dictionaryMapper::toDto)
            .filter(bookDictionaryDTO -> bookDictionaryDTO.getDictionaryWords()
                    .removeIf(dictionaryHasWordDTO -> dictionaryHasWordDTO.getWord().getTranslate() != null)
            );
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
     * @param targetLanguage - целеваой язык
     * @return - словарь
     */
    public BookDictionary createByText(String text, String baseLanguage, String targetLanguage) {
        BookDictionary dictionary = new BookDictionary();
        dictionary.setBaseLanguage(baseLanguage);
        dictionary.setTargetLanguage(targetLanguage);

        List<BookDictionaryHasWord> dictionaryWords = new ArrayList<>();
        dictionary.setDictionaryWords(dictionaryWords);
        try {
            List<CoreLabel> tokens = tokenizerService.getTokens(text);
            Map<TokenLight, Long> tokenCountMap = tokens.stream()
                .map(TokenLight::new)
                .peek(this::exceptionWord)
                .peek(this::exceptionWordAnyPOS)
                .peek(this::applyRuleTransformation)
                .peek(this::defaultWordTransformation)
                .filter(tokenLight -> tokenLight.getTag() != TagEnum.REMOVE)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            List<Word> newWords = new ArrayList<>();
            tokenCountMap.forEach((tokenLight, count) -> {
                Optional<Word> optionalWord = wordRepository.findByWordAndPartOfSpeech(tokenLight.getWord(), tokenLight.getTag().getTag());
                Word word = optionalWord.orElseGet(() -> {
                    Word newWord = wordService.create(tokenLight);
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

    /**
     * Преобразование токена в резулютирующее слово, есил другие преобразователи не отработали
     * Из токена берется лемма и тег
     */
    private void defaultWordTransformation(TokenLight tokenLight) {
        if (tokenLight.isDone()) return;
        tokenLight.setTag(TagEnum.REMOVE);
    }

    /**
     * Проверяет, это слово есть в исключениях
     */
    private void exceptionWord(TokenLight tokenLight) {
        if (tokenLight.isDone()) return;
        exceptionWordRepository.findByWordAndPartOfSpeech(tokenLight.getToken().word(), TagEnum.getByTag(tokenLight.getToken().tag()))
            .ifPresent(exceptionWord -> {
                tokenLight.setDone(true);
                tokenLight.setWord(exceptionWord.getWord());
                tokenLight.setTag(exceptionWord.getPartOfSpeech());
            });
    }

    /**
     * Проверяет слово на исключение, без зависимости от части речи
     */
    private void exceptionWordAnyPOS(TokenLight tokenLight) {
        if (tokenLight.isDone()) return;
        exceptionWordRepository.findByWordAndPartOfSpeech(tokenLight.getToken().word(), TagEnum.ANY)
            .ifPresent(exceptionWord -> {
                tokenLight.setDone(true);
                tokenLight.setWord(exceptionWord.getWord());
                tokenLight.setTag(exceptionWord.getPartOfSpeech());
            });
    }

    /**
     * Применяет правило в зависимости от части речи
     */
    private void applyRuleTransformation(TokenLight tokenLight) {
        if (tokenLight.isDone()) return;
        tokenRuleRepository.findByPartOfSpeech(TagEnum.getByTag(tokenLight.getToken().tag()))
            .ifPresent(tokenRule -> {
                tokenLight.setDone(true);
                tokenRule.getRule().getRule().accept(tokenLight, tokenRule.getTargetPOS());
            });
    }
}
