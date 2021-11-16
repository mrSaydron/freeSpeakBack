package ru.mrak.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mrak.model.entity.Book;
import ru.mrak.model.enumeration.GalleryItemTypeEnum;
import ru.mrak.service.FileService;
import ru.mrak.service.dto.gallery.item.GalleryItemBookDto;

@Mapper(componentModel = "spring")
public abstract class BookToGalleryItemMapper {

    @Autowired
    private FileService fileService;

    @Mapping(target = "pictureUrl", expression = "java( getPictureUrl(book) )")
    @Mapping(target = "type", expression = "java( getType() )" )
    public abstract GalleryItemBookDto toDto(Book book);

    protected String getPictureUrl(Book book) {
        return fileService.getUrl(book.getPictureId());
    }

    protected GalleryItemTypeEnum getType() {
        return GalleryItemTypeEnum.BOOK;
    }
}
