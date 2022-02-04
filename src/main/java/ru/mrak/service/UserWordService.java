package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;
import ru.mrak.model.entity.userWordProgress.UserWord;
import ru.mrak.model.entity.userWordProgress.UserWordHasProgress;
import ru.mrak.model.enumeration.UserWordLogTypeEnum;
import ru.mrak.model.enumeration.UserWordProgressTypeEnum;
import ru.mrak.repository.UserWordRepository;
import ru.mrak.repository.WordRepository;
import ru.mrak.dto.userWord.UserWordCriteria;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final UserWordLogService userWordLogService;
    private final UserSentencesService userSentencesService;

    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;

    @Value("${max-user-hearts}")
    private Integer maxUserHearts;

    @Value("${box-count}")
    private Integer boxCount;

    @Value("#{${box-count} + 1}")
    private Integer KNOW_BOX_NUMBER;

    // Коробка в которой слова не находятся в обучении, и будут взяты туда при запросе из карточек
    public static final int PRELIMINARY_BOX_NUMBER = 0;

    private static final int START_BOX_NUMBER = 1;

    // Ограничение на зпрос следующих для изучения слов
    private static final int NEXT_WORD_LIMIT = 10;

    @Transactional(readOnly = true)
    public Page<UserWord> findByCriteria(UserWordCriteria criteria, @NonNull Pageable pageable) {
        log.debug("find by criteria: {}, page: {}", criteria, pageable);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        final Specification<UserWord> specification
            = userWordQueryService.createSpecification(criteria, user);
        return userWordRepository.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public List<UserWord> findByCriteria(UserWordCriteria criteria) {
        log.debug("find by criteria: {}", criteria);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        final Specification<UserWord> specification
            = userWordQueryService.createSpecification(criteria, user);
        return userWordRepository.findAll(specification);
    }

    /**
     * Добавляет или оновляет слово из словаря пользователя
     */
    public void addOrUpdateWord(Long wordId) {
        log.debug("add word to user dictionary, word id: {}", wordId);
        Word word = wordRepository.getOne(wordId);
        addOrUpdateWord(word, PRELIMINARY_BOX_NUMBER, false);
    }

    /**
     * Добавляет или оновляет слово из словаря пользователя
     */
    public void addOrUpdateWord(Long wordId, int boxNumber, boolean fromTest) {
        log.debug("add word to user dictionary, word id: {}", wordId);
        Word word = wordRepository.getOne(wordId);
        addOrUpdateWord(word, boxNumber, fromTest);
    }

    /**
     * Добавляет или оновляет слово из словаря пользователя
     */
    public void addOrUpdateWord(Word word, int boxNumber, boolean fromTest) {
        log.debug("add word to user dictionary, word id: {}", word.getId());
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Optional<UserWord> progressOptional = userWordRepository.findByUserAndWord(user, word);
        if (!progressOptional.isPresent()) {
            UserWord userWord = new UserWord();

            userWord.setUser(user);
            userWord.setWord(word);
            userWord.setPriority(0);
            userWord.setFromTest(fromTest);

            for (UserWordProgressTypeEnum progressType : UserWordProgressTypeEnum.values()) {
                UserWordHasProgress userWordHasProgress = new UserWordHasProgress();
                userWord.getWordProgresses().add(userWordHasProgress);

                userWordHasProgress.setType(progressType);
                userWordHasProgress.setBoxNumber(boxNumber);
                userWordHasProgress.setSuccessfulAttempts(0);
            }

            userWordRepository.save(userWord);
            userWordLogService.create(userWord.getWord(), UserWordLogTypeEnum.ADD_TO_DICTIONARY);
        } else {
            UserWord userWord = progressOptional.get();
            userWord.setFromTest(fromTest);
            userWord.getWordProgresses()
                .forEach(userWordHasProgress -> userWordHasProgress.setBoxNumber(boxNumber));
        }
    }

    /**
     * Удаляет слово из словаря пользователя
     */
    public void removeWord(Long wordId) {
        log.debug("remove word from user dictionary, word id: {}", wordId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        userWordRepository.deleteAllByUserAndWord(user, wordRepository.getOne(wordId));
        userWordLogService.create(wordRepository.getOne(wordId), UserWordLogTypeEnum.REMOVE_FROM_DICTIONARY);
    }

    /**
     * Удаляет слова из словаря пользователя
     */
    public void removeWords(List<Long> wordIds) {
        log.debug("remove words from user dictionary, word ids: {}", wordIds);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        List<Word> words = wordIds.stream().map(wordRepository::getOne).collect(Collectors.toList());
        userWordRepository.deleteAllByUserAndWordIn(user, words);
        userWordLogService.create(words, UserWordLogTypeEnum.REMOVE_FROM_DICTIONARY);
    }

    /**
     * Удаляет слова из словаря пользователя по условию
     */
    public void removeAllWords(UserWordCriteria criteria) {
        log.debug("remove words from user dictionary by criteria: {}", criteria);
        List<UserWord> userWords = findByCriteria(criteria);
        userWordRepository.deleteAll(userWords);

        List<Word> words = userWords.stream().map(UserWord::getWord).collect(Collectors.toList());
        userWordLogService.create(words, UserWordLogTypeEnum.REMOVE_FROM_DICTIONARY);
    }

    /**
     * Сбрасывает прогресс слова пользователя
     */
    public void eraseWord(Long wordId) {
        log.debug("erase word progress, word id: {}", wordId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Optional<UserWord> userWordsOptional = userWordRepository.findByUserAndWord(user, wordRepository.getOne(wordId));
        if (userWordsOptional.isPresent()) {
            UserWord userWord = userWordsOptional.get();
            for (UserWordHasProgress userWordHasProgress : userWordsOptional.get().getWordProgresses()) {
                userWordHasProgress.setBoxNumber(PRELIMINARY_BOX_NUMBER);
            }
            userWordLogService.create(userWord.getWord(), UserWordLogTypeEnum.DO_NOT_KNOW);
        }
    }

    /**
     * Сбрасывает прогресс слов пользователя
     */
    public void eraseWords(List<Long> wordIds) {
        log.debug("erase word progress, word ids: {}", wordIds);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        List<Word> words = wordIds.stream().map(wordRepository::getOne).collect(Collectors.toList());
        userWordRepository.findAllByUserAndWordIn(user, words)
            .stream()
            .flatMap(userWord -> userWord.getWordProgresses().stream())
            .forEach(progress -> progress.setBoxNumber(PRELIMINARY_BOX_NUMBER));
        userWordLogService.create(words, UserWordLogTypeEnum.DO_NOT_KNOW);
    }

    /**
     * Сбрасывает прогресс слов пользователя по условию
     */
    public void eraseAllWords(UserWordCriteria criteria) {
        log.debug("erase word progress by criteria: {}", criteria);
        List<UserWord> userWords = findByCriteria(criteria);
        userWords.stream()
            .flatMap(userWord -> userWord.getWordProgresses().stream())
            .forEach(progress -> progress.setBoxNumber(PRELIMINARY_BOX_NUMBER));

        List<Word> words = userWords.stream().map(UserWord::getWord).collect(Collectors.toList());
        userWordLogService.create(words, UserWordLogTypeEnum.DO_NOT_KNOW);
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
        Optional<Integer> failAnswers = userWordRepository.getCountFailAnswersByUserAndDate(user, instant);
        int lastHearts = maxUserHearts - failAnswers.orElse(0);
        return Math.max(lastHearts, 0);
    }

    /**
     * Возвращает оставшиеся слова для изучения на текущий день
     */
    @Transactional(readOnly = true)
    public List<UserWord> getWordsOfDay() {
        log.debug("get words of day");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Instant createdDate = user.getCreatedDate();
        LocalDateTime createDay = LocalDateTime.ofInstant(createdDate, ZoneId.systemDefault()).toLocalDate().atStartOfDay();
        LocalDate currentDate = LocalDate.now();
        Duration between = Duration.between(createDay, currentDate.atStartOfDay());
        long days = between.toDays();

        List<Integer> boxes = new ArrayList<>();
        boxes.add(START_BOX_NUMBER);
        for (int box = START_BOX_NUMBER + 1; box <= boxCount; box++) {
            if ((days + Math.pow(2, box - 2)) % Math.pow(2, box - 1) == 0) {
                boxes.add(box);
                break;
            }
        }

        Instant currentInstant = currentDate.atStartOfDay().toInstant(ZoneOffset.UTC);

        return userWordRepository.findByAllByUserAndBoxesAndLessFailDateAndLessSuccessDate(user, boxes, currentInstant);
    }

    /**
     * Пользователь ответил неверно
     * Прогресс сбрасывается, слово перемещается в первую коробку
     * Записывается текущее время в поле с неправильным ответом
     * Увеличивается счетчик не правильных ответов
     */
    public void answerFail(Long userWordId, UserWordProgressTypeEnum type) {
        log.debug("user fail answer on word progress, userWordId: {}", userWordId);

        Optional<UserWord> userWordOptional = userWordRepository.findById(userWordId);
        if (userWordOptional.isPresent()) {
            UserWord userWord = userWordOptional.get();
            UserWordHasProgress userWordHasProgress = userWord.getWordProgresses().stream()
                .filter(progress -> Objects.equals(type, progress.getType()))
                .findFirst().orElseThrow(RuntimeException::new);
            // переноси слово в обучение
            userWordHasProgress.setBoxNumber(START_BOX_NUMBER);

            // счетчик неверных ответов
            Instant failLastDate = userWordHasProgress.getFailLastDate();
            LocalDateTime currentDate = LocalDate.now().atStartOfDay();
            Instant startDay = currentDate.toInstant(ZoneOffset.UTC);
            if (failLastDate != null && startDay.compareTo(failLastDate) < 0) {
                userWordHasProgress.setFailAttempts(userWordHasProgress.getFailAttempts() + 1);
            } else {
                userWordHasProgress.setFailAttempts(1);
            }
            userWordHasProgress.setFailLastDate(Instant.now());

            // сброс флага тестового изучения
            if (userWord.isFromTest()) userWord.setFromTest(false);

            userWordLogService.create(userWord.getWord(), UserWordLogTypeEnum.FAIL);
        }
    }

    /**
     * Пользователь ответил правильно
     * Если сегодня не было неправильных ответов, то слово пееносится в следующую коробку. Но не больше последней коробки + 1
     * Записывается текущее время в поле с правильным ответом
     */
    public void answerSuccess(Long userWordId, UserWordProgressTypeEnum type) {
        log.debug("user success answer on word progress, userWordId: {}", userWordId);

        Optional<UserWord> userWordOptional = userWordRepository.findById(userWordId);
        if (userWordOptional.isPresent()) {
            UserWord userWord = userWordOptional.get();
            UserWordHasProgress userWordHasProgress = userWord.getWordProgresses().stream()
                .filter(progress -> Objects.equals(type, progress.getType()))
                .findFirst().orElseThrow(RuntimeException::new);

            Instant currentInstant = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);

            if ((userWordHasProgress.getFailLastDate() == null || userWordHasProgress.getFailLastDate().compareTo(currentInstant) < 0)
                && userWordHasProgress.getBoxNumber() <= boxCount
            ) {
                if (userWordHasProgress.getBoxNumber() == PRELIMINARY_BOX_NUMBER) {
                    // переносит слово в обучение при первом ответе
                    userWordHasProgress.setBoxNumber(START_BOX_NUMBER);
                }
                userWordHasProgress.setBoxNumber(userWordHasProgress.getBoxNumber() + 1);
            }
            userWordHasProgress.setSuccessfulLastDate(Instant.now());

            if (userWord.isFromTest()) userWord.setFromTest(false);

            userWordLogService.create(userWord.getWord(), UserWordLogTypeEnum.SUCCESS);
            userSentencesService.findAndMarkSentenceByWord(userWord.getWord());
        }
    }

    /**
     * Отмечает слово выученным
     */
    public void knowWord(Long wordId, boolean fotTest) {
        log.debug("move word to know box. Word id: {}", wordId);
        addOrUpdateWord(wordId, KNOW_BOX_NUMBER, fotTest);

        if (!fotTest) {
            userWordLogService.create(wordRepository.getOne(wordId), UserWordLogTypeEnum.KNOW);
        }
    }

    /**
     * Отмечает слова выученными
     */
    public void knowWords(List<Long> wordIds) {
        log.debug("move words to know box. Word ids: {}", wordIds);
        wordIds.forEach(wordId -> knowWord(wordId, false));
    }

    /**
     * Отмечает слова выученными для теста
     */
    public void knowWordsByTest(List<Word> words) {
        log.debug("move words to know box from test");
        words.forEach(word -> knowWord(word.getId(), true));
    }

    /**
     * Отмечает слова выученными по условию
     */
    public void knowAllWords(UserWordCriteria criteria) {
        // todo сделать и для слов, которых нет у пользователя
        log.debug("move words to know box by criteria: {}", criteria);
        List<UserWord> userWords = findByCriteria(criteria);
        userWords.stream()
            .flatMap(userWord -> userWord.getWordProgresses().stream())
            .forEach(progress -> progress.setBoxNumber(KNOW_BOX_NUMBER));

        List<Word> words = userWords.stream().map(UserWord::getWord).collect(Collectors.toList());
        userWordLogService.create(words, UserWordLogTypeEnum.KNOW);
    }

    /**
     * Возвращает слово с прогрессом пользователя по идентификатору слова
     * Если у пользователя нет прогресса для этого слова, то в ответе будет только слово, без прогресса
     */
    public UserWord get(long wordId) {
        log.debug("get user word by word id: {}", wordId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        Optional<UserWord> userWordOptional = userWordRepository.findByUserAndWord(user, wordRepository.getOne(wordId));
        UserWord result = null;
        if (userWordOptional.isPresent()) {
            result = userWordOptional.get();
        } else {
            result = new UserWord();
            Optional<Word> wordOptional = wordRepository.findById(wordId);
            wordOptional.orElseThrow(RuntimeException::new);
            result.setWord(wordOptional.get());
        }
        return result;
    }

    /**
     * Сбрасывает приоритет изучения слов пользователя
     */
    public void resetPriority() {
        log.debug("reset words priority");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        userWordRepository.resetPriority(user.getId());
        userWordRepository.flush();
    }

    /**
     * Раставляет приоритет слов для изучения, для указанной книги
     */
    public void setPriorityByBook(long bookId) {
        log.debug("set priority for book id: {}", bookId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        userWordRepository.updatePriorityByBook(user.getId(), bookId);
    }

    /**
     * Возвращает допольнительные слова для изучения из словаря пользователя
     */
    public List<UserWord> getNextWords() {
        log.debug("get next words");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        return userWordRepository.getNextWords(user.getId(), NEXT_WORD_LIMIT);
    }
}
