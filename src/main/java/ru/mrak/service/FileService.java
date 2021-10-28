package ru.mrak.service;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mrak.model.SoundResult;
import ru.mrak.model.enumeration.BucketEnum;
import ru.mrak.service.s3.IS3Service;
import ru.mrak.util.CheckingInputStream;
import ru.mrak.util.FileUtil;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final IS3Service s3Service;

    public static final long FILE_MAX_SIZE = 10 * 1024 * 1024;
    public static final String PICTURE_BUCKET = "picture";
    public static final String AUDIO_BUCKET = "audio";

    //TODO проверка существуют ли бакеты

    /**
     * Сохранение изображения в хранилище
     * @param file загружаемый файл
     * @return имя корзины + имя файла
     */
    public String savePictureFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.debug("Save picture file: {}", file.getOriginalFilename());
        checkFile(file);
        Optional<String> anExtends = FileUtil.getExtends(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + anExtends.map(ext -> "." + ext).orElse("");
        return s3Service.saveFile(
            BucketEnum.PICTURE,
            fileName,
            file.getSize(),
            file.getContentType(),
            new CheckingInputStream(file.getInputStream(), checkPicture));
    }

    /**
     * Проверки загружаемого файла
     */
    private void checkFile(MultipartFile file) {
        if (file.getSize() > FILE_MAX_SIZE) {
            throw new RuntimeException("File size exceeded");
        }
    }

    private static final Set<Integer> fileHeaders = new HashSet<>(Arrays.asList(0xFFD8, 0x8950));

    private static final Function<CheckingInputStream, Boolean> checkPicture = stream -> {
        byte b0 = stream.getBuffer()[0];
        byte b1 = stream.getBuffer()[1];
        int header = (b0 & 0xFF) + (b1 & 0xFF)<<8;
        return !fileHeaders.contains(header);
    };

    /**
     * Созраняет аудио файл, файл созраняется без каких либо проверок
     * @return имя корзины + имя файла
     */
    public String saveAudioFile(SoundResult sound) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.debug("Save audio file");
        String fileName = UUID.randomUUID() + sound.getFileType();
        return s3Service.saveFile(BucketEnum.AUDIO, fileName, sound.getContentSize(), sound.getContentType(), sound.getSound());
    }

    /**
     * Возвращает ссылку на файл по его имени
     * @param fileName имя корзины + имя пакета
     * @return ссылка на файл
     */
    public String getUrl(String fileName) {
        log.debug("Return url to file name: {}", fileName);
        return s3Service.getUrl(fileName);
    }
}
