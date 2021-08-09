package ru.mrak.service.tarnslate;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.domain.Word;
import ru.mrak.domain.enumeration.TagEnum;
import ru.mrak.domain.yndex.PosEnum;
import ru.mrak.domain.yndex.WordDef;
import ru.mrak.domain.yndex.WordHead;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.FileService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TranslateService implements TranslateServiceInterface {

    private final Logger log = LoggerFactory.getLogger(TranslateService.class);

    private final YandexTranslateService yandexTranslateService;
    private final WooordhuntService wooordhuntService;
    private final FileService fileService;

    private final WordRepository wordRepository;

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

    @Override
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateWords(List<Word> words) {
        log.debug("Update words");
        words.forEach(this::updateWord);//todo сделать ассинхронным
        log.debug("Update words finish");
    }

    @Override
    @Async
    public void updateWord(Word word) {
        log.debug("Update word: {}", word);
        try {
            WordHead lookup = yandexTranslateService.requestWord(word.getWord());
            Set<PosEnum> posEnums = PosEnum.tagToPos.get(TagEnum.byTag.get(word.getPartOfSpeech()));
            if (posEnums != null) {
                WordDef chooseWord = null;
                if (lookup.getDef().size() > 0) {
                    if (lookup.getDef().size() == 1) {
                        chooseWord = lookup.getDef().get(0);
                    } else {
                        for (WordDef wordDef : lookup.getDef()) {
                            if (posEnums.contains(wordDef.getPos())) {
                                chooseWord = wordDef;
                                break;
                            }
                        }
                    }
                    // Перевод найден
                    if (chooseWord != null) {
                        String translate = yandexTranslateService.translateFilter(chooseWord);
                        word.setTranslate(translate);
                        word.setTranscription(chooseWord.getTs());

                        //todo сделать ассинхронным
                        fillAudioToWord(word);

                        wordRepository.save(word);
                    } else {
                        log.warn("Not found part of speech: {}, in translate for word: {}", word.getPartOfSpeech(), word.getWord());
                    }
                }
            } else {
                log.warn("Not find part of speech: {}, for word: {}", word.getPartOfSpeech(), word.getWord());
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    /**
     * Добавляем озвучку для слова
     */
    private void fillAudioToWord(Word word) {
        wooordhuntService.getSound(word.getWord())
            .ifPresent(soundResult -> {
                try {
                    String fileName = fileService.saveAudioFile(soundResult);
                    word.setUrlAudio(fileName);
                } catch (Exception e) {
                    log.warn("Can not upload audio file", e);
                }
            });
        word.setAudioFileRequested(true);
    }

    @Override
    public void updateAllAudio() {
        log.debug("Update audio for all words");
        wordRepository.findAll().forEach(this::fillAudioToWord);
    }

    @Override
    public void updateNoRequestAudio() {
        log.debug("Update audio for all not request words");
        wordRepository.findByAudioFileRequestedIsFalse().forEach(this::fillAudioToWord);
    }
}
