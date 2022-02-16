package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.bookUser.BookUser;
import ru.mrak.model.entity.bookUser.BookUserId;
import ru.mrak.repository.BookSentenceRepository;
import ru.mrak.repository.BookUserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserMarkedBookService {

    private final Logger log = LoggerFactory.getLogger(UserMarkedBookService.class);

    private final UserService userService;
    private final UserTimeService userTimeService;

    private final BookSentenceRepository bookSentenceRepository;
    private final BookUserRepository bookUserRepository;

    private final static int START_BOX_TO_LEARN = 2; // коробка со словами (и выше) для изучения для отмеченной книги

    /**
     * Возвращает предложения для тренировки из книги помеченной "для чтения"
     * Выбирает подходязие предложения из всей книги. Но возвращает только те которые идут по порядку начиная с
     * предложения с иденификатором кранащимся в bookUser.getLastReadSentenceId()
     */
    // todo оптимизация
    @Transactional(readOnly = true)
    public List<BookSentence> getSentencesFromMarkedBook() {
        log.debug("get sentence for train");
        List<BookSentence> result = Collections.EMPTY_LIST;
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        List<BookUser> bookUserList = bookUserRepository.findAllByUserAndIsReading(user, true);
        if (bookUserList.size() > 0) {
            BookUser bookUser = bookUserList.get(0);
            if (bookUser.getFinishDate() == null) {
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
            if (bookUser.getFinishDate() == null) {
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
        }
        return result;
    }

    /**
     * Перемещает указатель следующего предложения на указанное предложение
     * Если предложение последнее, то отмечаем книгу прочитанной
     */
    public void successTranslateFromMarkedBook(long bookSentenceId) {
        log.debug("success translate from marked book, bookSentenceId: {}", bookSentenceId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Optional<BookSentence> sentenceOptional = bookSentenceRepository.findById(bookSentenceId);
        if (sentenceOptional.isPresent()) {
            BookSentence bookSentence = sentenceOptional.get();
            Optional<BookUser> bookUserOptional = bookUserRepository.findById(new BookUserId(user.getId(), bookSentence.getBook().getId()));
            bookUserOptional.ifPresent(bookUser -> {
                bookUser.setLastReadSentenceId(bookSentenceId);

                Optional<BookSentence> nextSentenceOptional = bookSentenceRepository.findById(bookSentenceId + 1);
                if (
                    !nextSentenceOptional.isPresent() ||
                    nextSentenceOptional.get().getBook().getId().equals(bookUser.getBookId())
                ) {
                    bookUser.setFinishDate(userTimeService.getLocalTime());
                }
            });
        }
    }
}
