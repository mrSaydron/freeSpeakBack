package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;
import ru.mrak.model.entity.userWordProgress.UserWordProgress;
import ru.mrak.model.entity.userWordProgress.UserWordProgressId;
import ru.mrak.model.enumeration.UserWordProgressTypeEnum;
import ru.mrak.repository.UserWordProgressRepository;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.dto.userWord.UserWordCriteria;

import java.time.*;
import java.util.ArrayList;
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

    private final WordRepository wordRepository;
    private final UserWordProgressRepository userWordProgressRepository;

    @Value("${max-user-hearts}")
    private Integer maxUserHearts;

    @Value("${box-count}")
    private Integer boxCount;

    @Value("#{${box-count} + 1}")
    private Integer knowBoxNumber;

    private static final Integer PRELIMINARY_BOX_NUMBER = 0;
    private static final Integer START_BOX_NUMBER = 1;

    @Transactional(readOnly = true)
    public Page<UserWordProgress> findByCriteria(UserWordCriteria criteria, @NonNull Pageable pageable) {
        log.debug("find by criteria: {}, page: {}", criteria, pageable);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        final Specification<UserWordProgress> specification
            = userWordQueryService.createSpecification(criteria, user);
        return userWordProgressRepository.findAll(specification, pageable);
    }

    /**
     * Добавляет слово в словарь пользователя
     */
    public void addWord(Long wordId) {
        log.debug("add word to user dictionary, word id: {}", wordId);
        Word word = wordRepository.getOne(wordId);
        addWord(word);
    }

    /**
     * Добавляет слово в словарь пользователя
     */
    public void addWord(Word word) {
        log.debug("add word to user dictionary, word id: {}", word.getId());
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Optional<UserWordProgress> progressOptional = userWordProgressRepository.findById(new UserWordProgressId(
            user.getId(),
            word.getId(),
            UserWordProgressTypeEnum.direct
        ));
        if (!progressOptional.isPresent()) {
            UserWordProgress userWordProgress = new UserWordProgress();

            userWordProgress.setUserId(user.getId());
            userWordProgress.setWordId(word.getId());
            userWordProgress.setType(UserWordProgressTypeEnum.direct);

            userWordProgress.setWord(word);
            userWordProgress.setBoxNumber(PRELIMINARY_BOX_NUMBER);
            userWordProgress.setSuccessfulAttempts(0);

            userWordProgressRepository.save(userWordProgress);
        }
    }

    /**
     * Удаляет слово из словаря пользователя
     */
    public void removeWord(Long wordId) {
        log.debug("remove word from user dictionary, word id: {}", wordId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        userWordProgressRepository.deleteAllByUserIdAndWordId(user.getId(), wordId);
    }

    /**
     * Удаляет слова из словаря пользователя
     */
    public void removeWords(List<Long> wordIds) {
        log.debug("remove words from user dictionary, word ids: {}", wordIds);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        userWordProgressRepository.deleteAllByUserIdAndWordIdIn(user.getId(), wordIds);
    }

    /**
     * Удаляет слова из словаря пользователя по условию
     */
    public void removeAllWords(UserWordCriteria criteria) {
        log.debug("remove words from user dictionary by criteria: {}", criteria);
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);

        List<UserWordProgress> userWords = findByCriteria(criteria, pageRequest).getContent();
        userWordProgressRepository.deleteAll(userWords);
    }

    /**
     * Сбрасывает прогресс слова пользователя
     */
    public void eraseWord(Long wordId) {
        log.debug("erase word progress, word id: {}", wordId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        List<UserWordProgress> userWords = userWordProgressRepository.findAllByUserIdAndWordId(user.getId(), wordId);
        for (UserWordProgress userWord : userWords) {
            userWord.setBoxNumber(PRELIMINARY_BOX_NUMBER);
        }
    }

    /**
     * Сбрасывает прогресс слов пользователя
     */
    public void eraseWords(List<Long> wordIds) {
        log.debug("erase word progress, word ids: {}", wordIds);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        List<UserWordProgress> userWords = userWordProgressRepository.findAllByUserIdAndWordIdIn(user.getId(), wordIds);
        for (UserWordProgress userWord : userWords) {
            userWord.setBoxNumber(PRELIMINARY_BOX_NUMBER);
        }
    }

    /**
     * Сбрасывает прогресс слов пользователя по условию
     */
    public void eraseAllWords(UserWordCriteria criteria) {
        log.debug("erase word progress by criteria: {}", criteria);
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        List<UserWordProgress> userWords = findByCriteria(criteria, pageRequest).getContent();
        for (UserWordProgress userWord : userWords) {
            userWord.setBoxNumber(PRELIMINARY_BOX_NUMBER);
        }
    }

    /**
     * Возвращает осташиеся жизни пользователя для текущего дня
     */
    @Transactional(readOnly = true)
    public int getLeftHearts() {
        log.debug("get user left hearts");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        LocalDateTime currentDate = LocalDate.now().atStartOfDay();
        Instant instant = currentDate.toInstant(ZoneOffset.UTC);
        int lastHearts = maxUserHearts - userWordProgressRepository.getCountFailAnswersByUserIdAndDate(user.getId(), instant);
        return Math.max(lastHearts, 0);
    }

    /**
     * Возвращает оставшиеся слова для изучения на текущий день
     */
    @Transactional(readOnly = true)
    public List<UserWordProgress> getWordsOfDay() {
        log.debug("get words of day");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Instant createdDate = user.getCreatedDate();
        LocalDateTime createDay = LocalDateTime.ofInstant(createdDate, ZoneId.systemDefault()).toLocalDate().atStartOfDay();
        LocalDate currentDate = LocalDate.now();
        Duration between = Duration.between(createDay, currentDate.atStartOfDay());
        long days = between.toDays();

        List<Integer> boxes = new ArrayList<>();
        boxes.add(1);
        for (int box = 2; box <= boxCount; box++) {
            if ((days + Math.pow(2, box - 2)) % Math.pow(2, box - 1) == 0) {
                boxes.add(box);
                break;
            }
        }

        Instant currentInstant = currentDate.atStartOfDay().toInstant(ZoneOffset.UTC);

        return userWordProgressRepository.getByUserIdAndBoxesAndLessFailDateAndLessSuccessDate(user, boxes, currentInstant);
    }

    /**
     * Пользователь ответил неверно
     * Прогресс сбрасывается, слово перемещается в первую коробку
     * Записывается текущее вреся в поле с неправильным ответом
     */
    public void answerFail(Long wordId, UserWordProgressTypeEnum type) {
        log.debug("user fail answer on word progress");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        UserWordProgress userWordProgress = userWordProgressRepository.findById(new UserWordProgressId(
            user.getId(),
            wordId,
            type
        )).orElseThrow(RuntimeException::new);
        userWordProgress.setBoxNumber(START_BOX_NUMBER);
        userWordProgress.setFailLastDate(Instant.now());
    }

    /**
     * Пользователь ответил правильно
     * Если сегодня не было неправильных ответов, то слово пееносится в следующую коробку. Но не больше последней коробки + 1
     * Записывается текущее время в поле с правильным ответом
     */
    public void answerSuccess(Long wordId, UserWordProgressTypeEnum type) {
        log.debug("user success answer on word progress");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        UserWordProgress userWordProgress = userWordProgressRepository.findById(new UserWordProgressId(
            user.getId(),
            wordId,
            type
        )).orElseThrow(RuntimeException::new);
        Instant currentInstant = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);

        if ((userWordProgress.getFailLastDate() == null || userWordProgress.getFailLastDate().compareTo(currentInstant) < 0)
            && userWordProgress.getBoxNumber() <= boxCount
        ) {
            if (userWordProgress.getBoxNumber() == PRELIMINARY_BOX_NUMBER) {
                userWordProgress.setBoxNumber(START_BOX_NUMBER);
            }
            userWordProgress.setBoxNumber(userWordProgress.getBoxNumber() + 1);
        }
        userWordProgress.setSuccessfulLastDate(Instant.now());
    }

    /**
     * Отмечает слово выученным
     */
    public void knowWord(Long wordId) {
        log.debug("move word to know box. Word id: {}", wordId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        List<UserWordProgress> userWords = userWordProgressRepository.findAllByUserIdAndWordId(user.getId(), wordId);
        for (UserWordProgress userWord : userWords) {
            userWord.setBoxNumber(knowBoxNumber);
        }
    }

    /**
     * Отмечает слова выученными
     */
    public void knowWords(List<Long> wordIds) {
        log.debug("move words to know box. Word ids: {}", wordIds);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        List<UserWordProgress> userWords = userWordProgressRepository.findAllByUserIdAndWordIdIn(user.getId(), wordIds);
        for (UserWordProgress userWord : userWords) {
            userWord.setBoxNumber(knowBoxNumber);
        }
    }

    /**
     * Отмечает слова выученными по условию
     */
    public void knowAllWords(UserWordCriteria criteria) {
        log.debug("move words to know box by criteria: {}", criteria);
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        List<UserWordProgress> userWords = findByCriteria(criteria, pageRequest).getContent();
        for (UserWordProgress userWord : userWords) {
            userWord.setBoxNumber(knowBoxNumber);
        }
    }
}
