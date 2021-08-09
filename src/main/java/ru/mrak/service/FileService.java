package ru.mrak.service;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mrak.domain.SoundResult;
import ru.mrak.service.s3.MinioS3Service;
import ru.mrak.util.CheckingInputStream;
import ru.mrak.util.FileUtil;

import javax.mail.internet.ContentType;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final MinioS3Service s3Service;

    @Value("${s3-bucket-prefix}")
    public String s3BucketPrefix;

    @Value("${s3-endpoint}")
    public String s3Endpoint;

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
        String bucket = s3BucketPrefix + PICTURE_BUCKET;
        Optional<String> anExtends = FileUtil.getExtends(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + anExtends.map(ext -> "." + ext).orElse("");
        s3Service.saveFile(
            bucket,
            fileName,
            file.getSize(),
            null,
            new CheckingInputStream(file.getInputStream(), checkPicture));
        return bucket + "/" + fileName;
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
        String bucket = s3BucketPrefix + AUDIO_BUCKET;
        String fileName = UUID.randomUUID() + sound.getFileType();
        s3Service.saveFile(bucket, fileName, sound.getContentSize(), sound.getContentType(), sound.getSound());
        return bucket + "/" + fileName;
    }

    /**
     * Возвращает ссылку на файл по его имени
     * @param fileName имя корзины + имя пакета
     * @return ссылка на файл
     */
    public String getUrl(String fileName) {
        log.debug("Return url to file name: {}", fileName);
        return fileName != null ? s3Endpoint + "/" + fileName : null;
    }
}
