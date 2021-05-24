package ru.mrak.service.tarnslate;

import org.springframework.scheduling.annotation.Async;
import ru.mrak.domain.Word;

import java.util.List;

public interface TranslateService {
    void updateAllWords();
    void updateNoTranslateWords();

    @Async
    void updateWords(List<Word> words);

    @Async
    void updateWord(Word word);
}