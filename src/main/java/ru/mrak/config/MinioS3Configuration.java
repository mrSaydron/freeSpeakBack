package ru.mrak.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioS3Configuration {

    @Value("${s3-endpoint}")
    public String s3Endpoint;

    @Value("${s3-access-key}")
    public String accessKey;

    @Value("${s3-secret-key}")
    public String secretKey;

    @Bean
    public MinioClient getClient() {
        return MinioClient.builder()
            .endpoint(s3Endpoint)
            .credentials(accessKey, secretKey)
            .build();
    }

}
