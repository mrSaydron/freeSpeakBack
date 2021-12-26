package ru.mrak.service.galleryItem;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.bookUser.BookUser;
import ru.mrak.repository.BookRepository;
import ru.mrak.repository.BookUserRepository;
import ru.mrak.service.UserService;
import ru.mrak.dto.gallery.item.GalleryItemBookDto;
import ru.mrak.mapper.BookToGalleryItemMapper;

import java.util.List;
import java.util.Optional;

/**
 * Сервис формирует элемент галереи для книги отмеченной "для чтения"
 */
@Service
@RequiredArgsConstructor
public class GalleryItemMarkBookService implements GalleryItem<GalleryItemBookDto> {

    private final Logger log = LoggerFactory.getLogger(GalleryItemSentenceService.class);

    private final BookToGalleryItemMapper bookToGalleryItemMapper;

    private final UserService userService;

    private final BookUserRepository bookUserRepository;
    private final BookRepository bookRepository;

    @Override
    public Optional<GalleryItemBookDto> get() {
        log.debug("get read book item");
        Optional<GalleryItemBookDto> result = Optional.empty();

        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        List<BookUser> bookUserList = bookUserRepository.findAllByUserAndIsReading(user, true);
        if (bookUserList.size() > 0) {
            BookUser bookUser = bookUserList.get(0);
            Optional<Book> bookOptional = bookRepository.findById(bookUser.getBookId());
            if (bookOptional.isPresent()) {
                result = Optional.of(bookToGalleryItemMapper.toDto(bookOptional.get()));
            }
        }
        return result;
    }
}
