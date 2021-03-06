package ru.mrak.service.tarnslate;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.Word;
import ru.mrak.model.yndex.PosEnum;
import ru.mrak.model.yndex.WordDef;
import ru.mrak.model.yndex.WordHead;
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
    @Transactional
    public void updateWords(List<Word> words) {
        log.debug("Update words");
        words.forEach(this::updateWord);
        log.debug("Update words finish");
    }

    @Override
    public void updateWord(Word word) {
        log.debug("Update word: {}", word);
        try {
            WordHead lookup = yandexTranslateService.requestWord(word.getWord());
            Set<PosEnum> posEnums = PosEnum.tagToPos.get(word.getPartOfSpeech());
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
                    // ?????????????? ????????????
                    if (chooseWord != null) {
                        String translate = yandexTranslateService.translateFilter(chooseWord);
                        word.setTranslate(translate);
                        word.setTranscription(chooseWord.getTs());

                        //todo ?????????????? ????????????????????????
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
            log.warn("Catch exception on updateWord, word: {}", word.getWord());
            log.warn(e.getMessage());
        }
    }

    /**
     * ?????????????????? ?????????????? ?????? ??????????
     */
    private void fillAudioToWord(Word word) {
        wooordhuntService.getSound(word.getWord())
            .ifPresent(soundResult -> {
                try {
                    String fileId = fileService.saveAudioFile(soundResult);
                    word.setAudioId(fileId);
                } catch (Exception e) {
                    log.warn("Can not upload audio file for word: {}", word.getWord());
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
    @Transactional
    public void updateNoRequestAudio() {
        log.debug("Update audio for all not request words");
        wordRepository.findByAudioFileRequestedIsFalse().forEach(this::fillAudioToWord);
    }
}
