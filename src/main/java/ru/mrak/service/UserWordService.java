package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.domain.*;
import ru.mrak.domain.enumeration.UserWordProgressTypeEnum;
import ru.mrak.repository.UserDictionaryHasWordRepository;
import ru.mrak.repository.UserDictionaryRepository;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.dto.userWord.UserWordCriteria;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы со словарем пользователя
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserWordService {

    private final Logger log = LoggerFactory.getLogger(UserWordService.class);

    private final UserService userService;
    private final UserWordQueryService userWordQueryService;

    private final UserDictionaryHasWordRepository userDictionaryHasWordRepository;
    private final UserDictionaryRepository userDictionaryRepository;
    private final WordRepository wordRepository;

    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public Page<UserDictionaryHasWord> findByCriteria(UserWordCriteria criteria, @NonNull Pageable pageable) {
        log.debug("find by criteria: {}, page: {}", criteria, pageable);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        final Specification<UserDictionaryHasWord> specification
            = userWordQueryService.createSpecification(criteria, user);
        return userDictionaryHasWordRepository.findAll(specification, pageable);
    }

    /**
     * Добавляет слово в словарь пользователя
     */
    public void addWord(Long wordId) {
        log.debug("add word to user dictionary, word id: {}", wordId);
        UserDictionary userDictionary = getUserDictionary();
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        Optional<UserDictionaryHasWord> userHasWord
            = userDictionaryHasWordRepository.findByWordAndUser(wordRepository.getOne(wordId), user);

        if (!userHasWord.isPresent()) {
            UserDictionaryHasWord userDictionaryHasWord = newUserDictionaryHasWord(userDictionary, wordRepository.getOne(wordId));
            userDictionary.getDictionaryWords().add(userDictionaryHasWord);
            userDictionaryRepository.save(userDictionary);
            entityManager.flush();
            userDictionaryHasWordRepository.save(userDictionaryHasWord);
        }
    }

    /**
     * Возвращает словарь пользователя, или создает новый если его нет
     */
    public UserDictionary getUserDictionary() {
        log.debug("return user dictionary");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        return userDictionaryRepository.findFirstByUser(user)
            .orElseGet(() -> new UserDictionary(user, "rus", "eng"));
    }

    /**
     * Создает новое привязанное слово пользователя. Настраивает прогресс
     */
    private UserDictionaryHasWord newUserDictionaryHasWord(UserDictionary userDictionary, Word word) {
        UserDictionaryHasWord result = new UserDictionaryHasWord();
        result.setWord(word);
        result.setDictionary(userDictionary);
        result.setPriority(0);

        UserWordProgress userWordProgress = new UserWordProgress();
        userWordProgress.setBoxNumber(0);
        userWordProgress.setDictionaryWord(result);
        userWordProgress.setSuccessfulAttempts(0);
        userWordProgress.setType(UserWordProgressTypeEnum.direct);
        result.getWordProgresses().add(userWordProgress);

        return result;
    }

    /**
     * Удаляет слово из словаря пользователя
     */
    public void removeWord(Long wordId) {
        log.debug("remove word from user dictionary, word id: {}", wordId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        List<UserDictionary> userDictionaries = userDictionaryRepository.findByUser(user);
        userDictionaryHasWordRepository.deleteByWordAndUserDictionaries(wordRepository.getOne(wordId), userDictionaries);
    }

    /**
     * Удаляет слова из словаря пользователя
     */
    public void removeWords(List<Long> wordIds) {
        log.debug("remove words from user dictionary, word ids: {}", wordIds);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        List<UserDictionary> userDictionaries = userDictionaryRepository.findByUser(user);
        userDictionaryHasWordRepository.deleteByWordIdsAndUserDictionaries(wordIds, userDictionaries);
    }

    /**
     * Удаляет слова из словаря пользователя по условию
     */
    public void removeAllWords(UserWordCriteria criteria) {
        log.debug("remove words from user dictionary by criteria: {}", criteria);
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        List<UserDictionaryHasWord> userDictionaryHasWords = findByCriteria(criteria, pageRequest).getContent();
        userDictionaryHasWordRepository.deleteAll(userDictionaryHasWords);
    }

    /**
     * Сбрасывает прогресс слова пользователя
     */
    public void eraseWord(Long wordId) {
        log.debug("erase word progress, word id: {}", wordId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        UserDictionaryHasWord userDictionaryHasWord = userDictionaryHasWordRepository.findByWordAndUser(wordRepository.getOne(wordId), user)
            .orElseThrow(RuntimeException::new);
        for (UserWordProgress wordProgress : userDictionaryHasWord.getWordProgresses()) {
            wordProgress.setBoxNumber(0);
        }
    }

    /**
     * Сбрасывает прогресс слов пользователя
     */
    public void eraseWords(List<Long> wordIds) {
        log.debug("erase word progress, word ids: {}", wordIds);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        List<UserDictionaryHasWord> userDictionaryHasWords = userDictionaryHasWordRepository.findByWordIdsAndUser(wordIds, user);
        for (UserDictionaryHasWord userDictionaryHasWord : userDictionaryHasWords) {
            for (UserWordProgress wordProgress : userDictionaryHasWord.getWordProgresses()) {
                wordProgress.setBoxNumber(0);
            }
        }
    }

    /**
     * Сбрасывает прогресс слов пользователя по условию
     */
    public void eraseAllWords(UserWordCriteria criteria) {
        log.debug("erase word progress by criteria: {}", criteria);
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        List<UserDictionaryHasWord> userDictionaryHasWords = findByCriteria(criteria, pageRequest).getContent();
        for (UserDictionaryHasWord userDictionaryHasWord : userDictionaryHasWords) {
            for (UserWordProgress wordProgress : userDictionaryHasWord.getWordProgresses()) {
                wordProgress.setBoxNumber(0);
            }
        }
    }
}
