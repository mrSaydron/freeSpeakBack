package ru.mrak.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mrak.model.entity.Book;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.bookUser.BookUser;
import ru.mrak.model.entity.bookUser.BookUserId;
import ru.mrak.model.entity.bookUserKnow.BookUserKnow;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;
import ru.mrak.repository.BookUserKnowRepository;
import ru.mrak.repository.BookUserRepository;
import ru.mrak.service.FileService;
import ru.mrak.service.UserService;
import ru.mrak.service.dto.gallery.item.GalleryItemBookDto;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class BookToGalleryItemMapper {

    @Autowired private FileService fileService;
    @Autowired private UserService userService;

    @Autowired private BookUserRepository bookUserRepository;
    @Autowired private BookUserKnowRepository bookUserKnowRepository;

    @Mapping(target = "pictureUrl", expression = "java( getPictureUrl(book) )")
    @Mapping(target = "type", expression = "java( getType() )" )
    @Mapping(target = "know", expression = "java( know(book) )")
    @Mapping(target = "isReading", expression = "java( isReading(book) )")
    public abstract GalleryItemBookDto toDto(Book book);

    protected String getPictureUrl(Book book) {
        return fileService.getUrl(book.getPictureId());
    }

    protected GalleryItemTypeEnum getType() {
        return GalleryItemTypeEnum.BOOK;
    }

    Double know(Book book) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("Что то пошло не так"));
        Optional<BookUserKnow> knowOptional = bookUserKnowRepository.findByUserAndBook(user, book);
        return knowOptional.map(BookUserKnow::getKnow).orElse(null);
    }

    Boolean isReading(Book book) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RuntimeException("Что то пошло не так"));
        Optional<BookUser> bookUserOptional = bookUserRepository.findById(new BookUserId(user.getId(), book.getId()));
        return bookUserOptional.map(BookUser::getIsReading).orElse(false);
    }
}
