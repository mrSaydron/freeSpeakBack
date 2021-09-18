package ru.mrak.service.tarnslate;

import org.springframework.scheduling.annotation.Async;
import ru.mrak.domain.Word;

import java.util.List;

public interface TranslateServiceInterface {
    void updateAllWords();
    void updateNoTranslateWords();

    void updateWords(List<Word> words);

    @Async
    void updateWord(Word word);

    void updateAllAudio();
    void updateNoRequestAudio();
}
