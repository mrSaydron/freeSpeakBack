package ru.mrak.service.tarnslate;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.mrak.domain.Word;
import ru.mrak.domain.enumeration.TagEnum;
import ru.mrak.domain.yndex.PosEnum;
import ru.mrak.domain.yndex.Translate;
import ru.mrak.domain.yndex.WordDef;
import ru.mrak.domain.yndex.WordHead;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.WordService;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Primary
public class YandexTranslateService implements TranslateService {

    private final Logger log = LoggerFactory.getLogger(WordService.class);

    private final WordRepository wordRepository;

    @Value("${yandex-key}")
    private String key;

    private static final String YANDEX_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key=%s&lang=%s&text=%s";
    private static final String LANGUAGE = "en-ru";

    @Override
    public void updateAllWords() {
        log.debug("Update all words");
        wordRepository.findAll().forEach(this::updateWord);
    }

    @Override
    public void updateNoTranslateWords() {
        log.debug("Update all words without translate");
        wordRepository.findAllByTranslateIsNull().forEach(this::updateWord);
    }

    public boolean updateNoTranslateWords(int count) {
        log.debug("Update ten words without translate");
        List<Word> words = wordRepository.findByTranslateIsNull(count);
        if (words.size() > 0) {
            words.forEach(this::updateWord);
            return true;
        }
        return false;
    }

    @Override
    public void updateWords(List<Word> words) {
        log.debug("Update words");
        words.forEach(this::updateWord);
    }

    @Override
    public void updateWord(Word word) {
        log.debug("Update word: {}", word);
        try {
            WordHead lookup = lookup(word.getWord());
            Set<PosEnum> posEnums = PosEnum.tagToPos.get(TagEnum.byTag.get(word.getPartOfSpeech()));
            if (posEnums != null) {
                String translate = null;
                if (lookup.getDef().size() > 0) {
                    for (WordDef wordDef : lookup.getDef()) {
                        if (posEnums.contains(wordDef.getPos())) {
                            translate = translateFilter(wordDef);
                            break;
                        }
                    }
                } else {
                    translate = "-";
                }
                if (translate == null && lookup.getDef().size() == 1) {
                    translate = translateFilter(lookup.getDef().get(0));
                }
                if (translate != null) {
                    word.setTranslate(translate);
                } else {
                    log.warn("Not found part of speech: {}, in translate for word: {}", word.getPartOfSpeech(), word.getWord());
                }
            } else {
                log.warn("Not find part of speech: {}, for word: {}", word.getPartOfSpeech(), word.getWord());
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * Фильтрует слова их словарного списка
     */
    private String translateFilter(WordDef wordDef) {
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
    private WordHead lookup(String text) {
        log.debug("Request word from yandex: {}", text);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WordHead> responseEntity = restTemplate.getForEntity(String.format(YANDEX_URL, key, LANGUAGE, text), WordHead.class);

        return responseEntity.getBody();
    }

    public void test(String word) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(String.format(YANDEX_URL, key, LANGUAGE, word), String.class);
        System.out.println(responseEntity.getBody());
    }

}
