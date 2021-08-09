package ru.mrak.service.tarnslate;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mrak.domain.abby.AbbyTranslate;
import ru.mrak.domain.abby.ArticleModel;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbbyTranslateService {

    private final Logger log = LoggerFactory.getLogger(YandexTranslateService.class);

    private final RestTemplate restTemplate;

    @Value("${abby-key}")
    private String key;

    private String token;
    private LocalDateTime tokenExpired;
    private static final long TOKEN_LIFE_SECONDS = 322_560L; // 23 часа

    private static final int ENG = 1033;
    private static final int RUS = 1049;

    private static final String SERVICE_URL = "https://developers.lingvolive.com";

    public Optional<String> authenticate() {
        log.debug("Authenticate");

        String url = SERVICE_URL + "/api/v1.1/authenticate";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + key);
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> tokenResponse = restTemplate.postForEntity(url, entity, String.class);
        if (tokenResponse.getStatusCode() == HttpStatus.OK) {
            return Optional.ofNullable(tokenResponse.getBody());
        } else {
            throw new RuntimeException("Can't authenticate in abby, responseStatus: " + tokenResponse.getStatusCode());
        }
    }

    public Optional<String> getToken() {
        log.debug("Get token");

        if (token == null || tokenExpired == null || tokenExpired.compareTo(LocalDateTime.now()) > 0) {
            Optional<String> authenticate = authenticate();
            this.tokenExpired = LocalDateTime.now().plusSeconds(TOKEN_LIFE_SECONDS);
            this.token = authenticate.orElseThrow(() -> new RuntimeException("Not token"));
            return authenticate;
        }
        return Optional.of(this.token);
    }

    public List<ArticleModel> translation(String word) {
        log.debug("Get translation, word: {}", word);

        String token = getToken().orElseThrow(() -> new RuntimeException("No token"));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + token);

        String url = SERVICE_URL + "/api/v1/Translation?text={text}&srcLang={srcLang}&dstLang={dstLang}";
        HttpEntity<Object> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<List<ArticleModel>> exchange = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<ArticleModel>>() {},
            word,
            ENG,
            RUS
        );

        List<ArticleModel> body = exchange.getBody();
        return body;
    }

//    public void wordList() {
//
//    }
//
//    public void miniCard() {
//
//    }
//
//    public void search() {
//
//    }
//
//    public void article() {
//
//    }
//
//    public void suggest() {
//
//    }
//
//    public void wordForm() {
//
//    }
//
    public Optional<InputStream> sound(String word) {
        //todo
        throw new RuntimeException("Not implemented");
    }
}
