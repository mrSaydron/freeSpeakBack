package ru.mrak.service.s3;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.mrak.model.enumeration.BucketEnum;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Profile("dev")
@Service
@RequiredArgsConstructor
public class S3MinioService implements IS3Service {

    private final Logger log = LoggerFactory.getLogger(S3MinioService.class);

    private final MinioClient minioClient;

    @Value("${s3-endpoint}")
    public String s3Endpoint;

    private static final Map<BucketEnum, String> BUCKET_NAMES = new HashMap<>();
    static {
        BUCKET_NAMES.put(BucketEnum.PICTURE, "petpicture");
        BUCKET_NAMES.put(BucketEnum.AUDIO, "petaudio");
    }
    //todo создание бакетов

    @Override
    public String saveFile(BucketEnum bucket, String fileName, Long fileSize, String contentType, InputStream stream) throws IOException {
        log.debug("Save file, bucket: {}, name: {}", bucket, fileName);
        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .stream(stream, fileSize, -1)
                    .bucket(BUCKET_NAMES.get(bucket))
                    .object(fileName)
                    .contentType(contentType)
                    .build()
            );
        } catch (ServerException |
            InsufficientDataException |
            ErrorResponseException |
            NoSuchAlgorithmException |
            InvalidKeyException |
            InvalidResponseException |
            XmlParserException |
            InternalException e
        ) {
            throw new IOException(e);
        }
        return BUCKET_NAMES.get(bucket) + "/" + fileName;
    }

    @Override
    public String getUrl(String bucketAndFileName) {
        log.debug("Return url to file name: {}", bucketAndFileName);
        return bucketAndFileName != null ? s3Endpoint + "/" + bucketAndFileName : null;
    }

}
