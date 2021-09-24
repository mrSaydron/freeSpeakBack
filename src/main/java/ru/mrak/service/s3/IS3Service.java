package ru.mrak.service.s3;

import ru.mrak.domain.enumeration.BucketEnum;

import java.io.IOException;
import java.io.InputStream;

public interface IS3Service {

    /**
     * Сохраняет файлв в хранилище
     * @return - имя бакета / имя файла
     */
    String saveFile(BucketEnum bucket, String fileName, Long fileSize, String contentType, InputStream stream) throws IOException;

    /**
     * Возвращает ссылку на файл
     * @param fileName - имя бакета / имя файла
     */
    String getUrl(String bucketAndFileName);
}