package ru.mrak.service.tarnslate;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.mrak.model.yndex.Translate;
import ru.mrak.model.yndex.WordDef;
import ru.mrak.model.yndex.WordHead;

import java.util.OptionalInt;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class YandexTranslateService {

    private final Logger log = LoggerFactory.getLogger(YandexTranslateService.class);

    private final RestTemplate restTemplate;

    @Value("${yandex-key}")
    private String key;

    private static final String YANDEX_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=%s&lang=%s&text=%s";
    private static final String LANGUAGE = "en-ru";

    /**
     * Фильтрует слова их словарного списка
     */
    public String translateFilter(WordDef wordDef) {
        OptionalInt max = wordDef.getTr().stream().mapToInt(Translate::getFr).max();
        max.orElseThrow(() -> new RuntimeException("Word has not have frequency"));
        int maxFrequency = max.getAsInt();
        return wordDef.getTr()
            .stream()
            .filter(tr -> tr.getFr() == maxFrequency)
            .map(Translate::getText)
            .collect(Collectors.joining("; "));
    }

    /**
     * Запрашивает перевод слова
     */
    public WordHead requestWord(String text) {
        log.debug("Request word from yandex: {}", text);
        ResponseEntity<WordHead> responseEntity = restTemplate.getForEntity(String.format(YANDEX_URL, key, LANGUAGE, text), WordHead.class);

        return responseEntity.getBody();
    }

}
