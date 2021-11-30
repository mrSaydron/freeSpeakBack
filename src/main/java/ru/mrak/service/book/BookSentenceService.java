package ru.mrak.service.book;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.bookUser.BookUser;
import ru.mrak.model.entity.bookUser.BookUserId;
import ru.mrak.model.entity.userHasSentences.UserHasSentences;
import ru.mrak.model.entity.userHasSentences.UserHasSentencesId;
import ru.mrak.model.entity.userWordProgress.UserWord;
import ru.mrak.repository.BookSentenceRepository;
import ru.mrak.repository.BookUserRepository;
import ru.mrak.repository.UserHasSentencesRepository;
import ru.mrak.repository.UserWordRepository;
import ru.mrak.service.UserService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
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
    private final BookUserRepository bookUserRepository;

    private final static int START_BOX_TO_READ_SENTENCE = 3; // коробка со словами (и выше) из которой бурутся слова для изучения предложений
    private final static int SENTENCE_TO_READ = 10;
    private final static int START_BOX_TO_LEARN = 2; // коробка со словами (и выше) для изучения для отмеченной книги

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
    public List<BookSentence> getSentences(long bookId) {
        log.debug("get all sentence for book, bookId: {}", bookId);
        List<BookSentence> bookSentences = bookSentenceRepository.findAllByBookId(bookId);
        bookSentences.sort(Comparator.comparing(BookSentence::getId));
        return bookSentences;
    }

    /**
     * Возвращает предложения для тренировки из книги помеченной "для чтения"
     */
    @Transactional(readOnly = true)
    public List<BookSentence> getSentencesFromMarkedBook() {
        log.debug("get sentence for train");
        List<BookSentence> result = Collections.EMPTY_LIST;
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        List<BookUser> bookUserList = bookUserRepository.findAllByUserAndIsReading(user, true);
        if (bookUserList.size() > 0) {
            BookUser bookUser = bookUserList.get(0);
            long startSentenceId = bookUser.getLastReadSentenceId() != null ? bookUser.getLastReadSentenceId() : 0;
            List<BookSentence> sentences = bookSentenceRepository.findAllWhereAllLearnedUserWords(
                user.getId(),
                bookUser.getBookId(),
                START_BOX_TO_LEARN,
                startSentenceId
            );
            result = new ArrayList<>();
            Long lastSentenceId = bookUser.getLastReadSentenceId();
            for (BookSentence sentence : sentences) {
                if (lastSentenceId == null || (lastSentenceId + 1) == sentence.getId()) {
                    result.add(sentence);
                    lastSentenceId = sentence.getId();
                } else {
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Проверяет, есть ли предложения из книги помеченной "для чтения" доступные для чтения
     */
    @Transactional(readOnly = true)
    public boolean hasMarkSentence() {
        log.debug("has sentences to learn");
        boolean result = false;
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        List<BookUser> bookUserList = bookUserRepository.findAllByUserAndIsReading(user, true);
        if (bookUserList.size() > 0) {
            BookUser bookUser = bookUserList.get(0);
            Long lastReadSentenceId = bookUser.getLastReadSentenceId();
            boolean hasSentence = false;
            if (lastReadSentenceId == null) {
                Optional<BookSentence> bookSentenceOptional = bookSentenceRepository.findFirstByBook(bookUser.getBook());
                if (bookSentenceOptional.isPresent()) {
                    lastReadSentenceId = bookSentenceOptional.get().getId();
                    hasSentence = true;
                }
            } else {
                lastReadSentenceId = lastReadSentenceId + 1;
                Optional<BookSentence> nextSentenceOptional = bookSentenceRepository.findById(lastReadSentenceId);
                if (nextSentenceOptional.isPresent()) {
                    lastReadSentenceId = nextSentenceOptional.get().getId();
                    hasSentence = true;
                }
            }

            if (hasSentence) {
                result = bookSentenceRepository.hasAllUserWordInSentence(
                    user.getId(),
                    lastReadSentenceId,
                    START_BOX_TO_LEARN
                );
            }
        }
        return result;
    }

    /**
     * Перемещает указатель следующего предложения на указанное предложение
     */
    public void successTranslateFromMarkedBook(long bookSentenceId) {
        log.debug("success translate from marked book, bookSentenceId: {}", bookSentenceId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Optional<BookSentence> sentenceOptional = bookSentenceRepository.findById(bookSentenceId);
        if (sentenceOptional.isPresent()) {
            BookSentence bookSentence = sentenceOptional.get();
            Optional<BookUser> bookUserOptional = bookUserRepository.findById(new BookUserId(user.getId(), bookSentence.getBook().getId()));
            bookUserOptional.ifPresent(bookUser -> bookUser.setLastReadSentenceId(bookSentenceId));
        }
    }
}
