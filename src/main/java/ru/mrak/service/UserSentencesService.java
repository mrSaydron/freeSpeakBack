package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;
import ru.mrak.model.entity.userHasSentences.UserHasSentences;
import ru.mrak.model.entity.userHasSentences.UserHasSentencesId;
import ru.mrak.model.entity.userWordProgress.UserWord;
import ru.mrak.repository.BookSentenceRepository;
import ru.mrak.repository.UserHasSentencesRepository;
import ru.mrak.repository.UserWordRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис для работы с предложениями пользователя
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserSentencesService {

    private final Logger log = LoggerFactory.getLogger(UserSentencesService.class);

    private final UserService userService;

    private final UserHasSentencesRepository userHasSentencesRepository;
    private final UserWordRepository userWordRepository;
    private final BookSentenceRepository bookSentenceRepository;

    private final static int START_BOX_TO_READ_SENTENCE = 3; // коробка со словами (и выше) из которой бурутся слова для изучения предложений
    private final static int SENTENCE_TO_READ = 10;
    private final static int SENTENCE_TO_READ_FOR_WORD = 2; // количество предложений для изучения на одно слово

    /**
     * Подбирает предложения для изучения пользователем
     * Предложения выбираются только состоящие из слов пользователя находящиеся в коробке выше указанной,
     * и включающие переданные слова
     */
    @Transactional(readOnly = true)
    public List<BookSentence> pickUpSentenceForUser() {
        log.debug("Get sentence for user");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Instant startDay = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        List<UserWord> words = userWordRepository.findAllByUserAndGreaterThanSuccessDate(user, startDay);
        if (words.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<Long> wordIds = words.stream().map(userWord -> userWord.getWord().getId()).collect(Collectors.toList());
            return bookSentenceRepository.findAllForLearnByUserId(
                user.getId(),
                START_BOX_TO_READ_SENTENCE,
                wordIds,
                startDay,
                SENTENCE_TO_READ);
        }
    }

    /**
     * Возвращает предложения помеченные для изучения
     */
    @Transactional(readOnly = true)
    public List<BookSentence> getMarkedSentences() {
        log.debug("Get marked sentences");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Instant startDay = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        return bookSentenceRepository.findAllMarkedByUser(user.getId(), startDay);
    }

    /**
     * Подбирает предложения для указанного слова и помечает из для изучения
     */
    public void findAndMarkSentenceByWord(Word word) {
        log.debug("Find and mark sentence by word: {}", word);
        List<BookSentence> sentenceByWord = getSentenceByWord(word, SENTENCE_TO_READ_FOR_WORD);
        markSentencesToRead(sentenceByWord);
    }

    /**
     * Подбирает предложения для изучения для переданного слова
     */
    @Transactional(readOnly = true)
    public List<BookSentence> getSentenceByWord(Word word, int sentenceToRead) {
        log.debug("Get sentence by word: {}", word);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Instant startDay = LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
        List<Long> wordIds = Collections.singletonList(word.getId());
        return bookSentenceRepository.findAllForLearnByUserId(
            user.getId(),
            START_BOX_TO_READ_SENTENCE,
            wordIds,
            startDay,
            sentenceToRead);
    }

    /**
     * Отмечает предложения для изучения в тот же день
     */
    public void markSentencesToRead(List<BookSentence> sentences) {
        log.debug("Mark sentences to read: {}", sentences);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Instant now = Instant.now();
        for (BookSentence sentence : sentences) {
            UserHasSentences userHasSentences = new UserHasSentences(user.getId(), sentence.getId(), null, null, now);
            userHasSentencesRepository.save(userHasSentences);
        }
    }

    /**
     * Помечает предложение успешно переведенным
     */
    public void successTranslate(long bookSentenceId) {
        log.debug("User success translate sentence, id: {}", bookSentenceId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        UserHasSentences userHasSentences = null;
        Optional<UserHasSentences> userHasSentencesOptional = userHasSentencesRepository
            .findById(new UserHasSentencesId(user.getId(), bookSentenceId));
        if (userHasSentencesOptional.isPresent()) {
            userHasSentences = userHasSentencesOptional.get();
            userHasSentences.setSuccessfulLastDate(Instant.now());
        } else {
            userHasSentences = new UserHasSentences(user.getId(), bookSentenceId, Instant.now(), null, null);
        }
        userHasSentencesRepository.save(userHasSentences);
    }

    /**
     * Помечает предложение не переведенным
     */
    public void failTranslate(long bookSentenceId) {
        log.debug("User fail translate sentence, id: {}", bookSentenceId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        UserHasSentences userHasSentences = null;
        Optional<UserHasSentences> userHasSentencesOptional = userHasSentencesRepository
            .findById(new UserHasSentencesId(user.getId(), bookSentenceId));
        if (userHasSentencesOptional.isPresent()) {
            userHasSentences = userHasSentencesOptional.get();
            userHasSentences.setFailLastDate(Instant.now());
        } else {
            userHasSentences = new UserHasSentences(user.getId(), bookSentenceId, null, Instant.now(), null);
        }
        userHasSentencesRepository.save(userHasSentences);
    }
}
