package ru.mrak.service.s3;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MinioS3Service {

    private final Logger log = LoggerFactory.getLogger(MinioS3Service.class);

    private final MinioClient minioClient;

    public void saveFile(String bucket, String fileName, Long fileSize, InputStream stream) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.debug("Save file, bucket: {}, name: {}", bucket, fileName);
        minioClient.putObject(
            PutObjectArgs.builder()
            .stream(stream, fileSize, -1)
            .bucket(bucket)
            .object(fileName)
            .build()
        );
    }

}
