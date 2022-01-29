package ru.mrak.service.book;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.bookUser.BookUser;
import ru.mrak.model.entity.bookUser.BookUserId;
import ru.mrak.repository.BookRepository;
import ru.mrak.repository.BookUserRepository;
import ru.mrak.service.UserService;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookUserService {

    private final Logger log = LoggerFactory.getLogger(BookUserService.class);

    private final UserService userService;

    private final BookUserRepository bookUserRepository;
    private final BookRepository bookRepository;

    /**
     * Сбрасывает книги "для чтения" для текущего пользователя
     */
    public void resetBookIsRead() {
        log.debug("reset book 'is read'");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        bookUserRepository.resetIsReadByUserId(user.getId());
        bookUserRepository.flush();
    }

    /**
     * Устанавливает признак "для чтения" для указанной книги
     */
    public void setBookIsRead(long bookId) {
        log.debug("set book 'is read', book id: {}", bookId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        Optional<BookUser> bookUserOptional = bookUserRepository.findById(new BookUserId(user.getId(), bookId));
        if (bookUserOptional.isPresent()) {
            bookUserOptional.get().setIsReading(true);
        } else {
            BookUser bookUser = new BookUser(user.getId(), bookId);
            bookUser.setUser(user);
            bookUser.setBook(bookRepository.getOne(bookId));
            bookUser.setIsReading(true);
            bookUserRepository.save(bookUser);
        }
        bookUserRepository.flush();
    }
}
