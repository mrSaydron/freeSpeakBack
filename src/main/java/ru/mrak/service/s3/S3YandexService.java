package ru.mrak.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.mrak.domain.enumeration.BucketEnum;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Profile("prod")
@Service
@RequiredArgsConstructor
public class S3YandexService implements IS3Service {

    private final Logger log = LoggerFactory.getLogger(S3YandexService.class);

    public final AmazonS3 s3;

    private static final String s3Endpoint = "https://storage.yandexcloud.net/";

    private static final Map<BucketEnum, String> BUCKET_NAMES = new HashMap<>();
    static {
        BUCKET_NAMES.put(BucketEnum.PICTURE, "free-speak-picture");
        BUCKET_NAMES.put(BucketEnum.AUDIO, "free-speak-audio");
    }

    @Override
    public String saveFile(
        BucketEnum bucket,
        String fileName,
        Long fileSize,
        String contentType,
        InputStream stream
    ) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileSize);
        objectMetadata.setContentType(contentType);
        s3.putObject(BUCKET_NAMES.get(bucket), fileName, stream, objectMetadata);

        return BUCKET_NAMES.get(bucket) + "/" + fileName;
    }

    @Override
    public String getUrl(String bucketAndFileName) {
        log.debug("Return url to file name: {}", bucketAndFileName);
        return bucketAndFileName != null ? s3Endpoint + bucketAndFileName : null;
    }
}
