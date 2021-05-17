package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.mrak.service.s3.MinioS3Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    private final MinioS3Service s3Service;

    @Value("${s3-bucket-prefix}")
    public String s3BucketPrefix;

    public String savePictureFile(MultipartFile file) {
        log.debug("Save picture file: {}", file.getName());
        String bucket = s3BucketPrefix + "picture";
//        String fileName = UUID.randomUUID() +
        return null;
    }
}
