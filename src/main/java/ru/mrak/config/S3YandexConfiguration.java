package ru.mrak.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class S3YandexConfiguration {

    @Bean
    public AmazonS3 amazonS3Configuration() {
        AWSCredentials credentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return "DTGoBDoFRleHMxWfFTYx";
            }

            @Override
            public String getAWSSecretKey() {
                return "zBKI8AXmJ0lx1MqoBr8HV92F7yBr9v4qasINoZ4n";
            }
        };

        return AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .withEndpointConfiguration(
                new AmazonS3ClientBuilder.EndpointConfiguration(
                    "storage.yandexcloud.net","ru-central1"
                )
            )
            .build();
    }

}
