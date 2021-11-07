package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.model.entity.User;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookSentenceService {

    private final Logger log = LoggerFactory.getLogger(BookSentenceService.class);

    private final UserService userService;

    private final BookSentenceRepository bookSentenceRepository;
    private final UserHasSentencesRepository userHasSentencesRepository;
    private final UserWordRepository userWordRepository;

    private final static int START_BOX_TO_READ_SENTENCE = 3;
    private final static int SENTENCE_TO_READ = 10;

    /**
     * Возвращает список предложения для изучения пользователем
     * Предложения выбираются только состоящие из слов пользователя находящиеся в коробке выше указанной,
     * и включающие переданные слова
     */
    @Transactional(readOnly = true)
    public List<BookSentence> getSentenceForUser() {
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
            userHasSentences = new UserHasSentences(user.getId(), bookSentenceId, Instant.now(), null);
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
            userHasSentences = new UserHasSentences(user.getId(), bookSentenceId, null, Instant.now());
        }
        userHasSentencesRepository.save(userHasSentences);
    }

    /**
     * Возвращает предложения указанной книги
     */
    @Transactional(readOnly = true)
    public List<BookSentence> getSentences(Long bookId) {
        List<BookSentence> bookSentences = bookSentenceRepository.findAllByBookId(bookId);
        bookSentences.sort(Comparator.comparing(BookSentence::getId));
        return bookSentences;
    }

}
