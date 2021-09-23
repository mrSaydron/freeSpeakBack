package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.domain.entity.ExceptionWord;
import ru.mrak.domain.TokenLight;
import ru.mrak.domain.enumeration.TagEnum;
import ru.mrak.repository.ExceptionWordRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ExceptionWordService {

    private final ExceptionWordRepository exceptionWordRepository;


    @Transactional(readOnly = true)
    public ExceptionWordFinder getExceptionWordFinder() {
        List<ExceptionWord> exceptionWords = exceptionWordRepository.findAll();
        return new ExceptionWordFinder(exceptionWords);
    }

    /**
     * Проверяет переданные слова на исключения
     */
    public static class ExceptionWordFinder {
        private final Map<Key, ExceptionWord> exceptionWordMap;
        private final Map<String, ExceptionWord> anyPOSExceptionWordMap;

        private ExceptionWordFinder(List<ExceptionWord> exceptionWords) {
            exceptionWordMap = exceptionWords.stream()
                .filter(exceptionWord -> exceptionWord.getPartOfSpeech() != TagEnum.ANY)
                .collect(Collectors.toMap(Key::new, Function.identity()));
            anyPOSExceptionWordMap = exceptionWords.stream()
                .filter(exceptionWord -> exceptionWord.getPartOfSpeech() == TagEnum.ANY)
                .collect(Collectors.toMap(ExceptionWord::getWord, Function.identity()));
        }

        public void checkExceptionWord(TokenLight tokenLight) {
            if (tokenLight.isDone()) return;
            Key key = new Key(tokenLight.getToken().word(), tokenLight.getToken().tag());
            ExceptionWord exceptionWord = exceptionWordMap.get(key);
            if (exceptionWord != null) {
                tokenLight.setWord(exceptionWord.getTargetWord());
                tokenLight.setTag(exceptionWord.getTargetPartOfSpeech());
                tokenLight.setDone(true);
                return;
            }
            ExceptionWord exceptionWordAnyPOS = anyPOSExceptionWordMap.get(tokenLight.getToken().word());
            if (exceptionWordAnyPOS != null) {
                tokenLight.setWord(exceptionWordAnyPOS.getTargetWord());
                tokenLight.setTag(exceptionWordAnyPOS.getTargetPartOfSpeech());
                tokenLight.setDone(true);
                return;
            }
        }
    }

    private static class Key {
        public String word;
        public String partOfSpeech;

        public Key(String word, String partOfSpeech) {
            this.word = word;
            this.partOfSpeech = partOfSpeech;
        }

        public Key(ExceptionWord exceptionWord) {
            this.word = exceptionWord.getWord();
            this.partOfSpeech = exceptionWord.getPartOfSpeech().getTag();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return Objects.equals(word, key.word) &&
                Objects.equals(partOfSpeech, key.partOfSpeech);
        }

        @Override
        public int hashCode() {
            return Objects.hash(word, partOfSpeech);
        }
    }

}
