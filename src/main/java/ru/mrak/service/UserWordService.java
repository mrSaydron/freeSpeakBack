package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.domain.*;
import ru.mrak.domain.enumeration.UserWordProgressTypeEnum;
import ru.mrak.repository.UserDictionaryHasWordRepository;
import ru.mrak.repository.UserDictionaryRepository;
import ru.mrak.repository.WordRepository;

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

    private final UserService userService;

    private final UserDictionaryHasWordRepository userDictionaryHasWordRepository;
    private final UserDictionaryRepository userDictionaryRepository;
    private final WordRepository wordRepository;

    private final EntityManager entityManager;

    /**
     * Добавляет слово в словарь пользователя
     */
    public void addWord(Long wordId) {
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
     * Удаляет слово из словаря пользователя
     */
    public void removeWord(Long wordId) {
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        List<UserDictionary> userDictionaries = userDictionaryRepository.findByUser(user);
        userDictionaryHasWordRepository.deleteByWordAndUser(wordRepository.getOne(wordId), userDictionaries);
    }

    /**
     * Возвращает словарь пользователя, или создает новый если его нет
     */
    public UserDictionary getUserDictionary() {
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

}
