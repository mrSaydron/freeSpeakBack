package ru.mrak.service;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.model.TokenLight;
import ru.mrak.model.entity.BookSentence;
import ru.mrak.model.entity.BookSentenceHasWord;
import ru.mrak.model.entity.Word;
import ru.mrak.model.enumeration.PartOfSpeechEnum;
import ru.mrak.repository.ExceptionWordRepository;
import ru.mrak.repository.TokenRuleRepository;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.book.BookService;
import ru.mrak.service.tarnslate.TranslateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис работающий с текстом, разбиение текста на предложения, на токены....
 */
@Service
@RequiredArgsConstructor
public class TextService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final WordService wordService;
    private final TranslateService translateService;

    private final ExceptionWordRepository exceptionWordRepository;
    private final TokenRuleRepository tokenRuleRepository;
    private final WordRepository wordRepository;

    private final StanfordCoreNLP pipeline;

    /**
     * Разбивает текст на предложеня и на слова в них
     */
    public List<BookSentence> createByText(String text) {
        log.debug("CreateByText start");

        List<BookSentence> result = new ArrayList<>();
        try {
            CoreDocument document = new CoreDocument(text);
            pipeline.annotate(document);

            List<Word> newWords = new ArrayList<>();
            List<CoreSentence> sentences = document.sentences();
            for (CoreSentence sentence : sentences) {
                BookSentence bookSentence = new BookSentence();
                result.add(bookSentence);

                List<CoreLabel> tokens = sentence.tokens();
                tokens.stream()
                    .map(TokenLight::new)
                    .peek(this::exceptionWord)
                    .peek(this::exceptionWordAnyPOS)
                    .peek(this::applyRuleTransformation)
                    .peek(this::defaultWordTransformation)
                    .forEach(tokenLight -> {
                        BookSentenceHasWord sentenceWord = new BookSentenceHasWord();
                        bookSentence.getWords().add(sentenceWord);

                        sentenceWord.setWord(tokenLight.getToken().word());
                        sentenceWord.setAfterWord(tokenLight.getToken().after());
                        sentenceWord.setPartOfSpeech(PartOfSpeechEnum.getByTag(tokenLight.getTag().getTag()));

                        if (tokenLight.getTag() != PartOfSpeechEnum.REMOVE) {
                            Optional<Word> translateOptional = wordRepository.findByWordAndPartOfSpeech(tokenLight.getWord(), tokenLight.getTag());
                            Word translate = translateOptional.orElseGet(() -> {
                                Word newWord = wordService.create(tokenLight);
                                newWords.add(newWord);
                                return newWord;
                            });
                            sentenceWord.setTranslate(translate);
                        }
                    });
            }
            translateService.updateWords(newWords);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.debug("CreateByText finish");
        return result;
    }

    /**
     * Преобразование токена в резулютирующее слово, есил другие преобразователи не отработали
     * Из токена берется лемма и тег
     */
    private void defaultWordTransformation(TokenLight tokenLight) {
        if (tokenLight.isDone()) return;
        tokenLight.setTag(PartOfSpeechEnum.REMOVE);
    }

    /**
     * Проверяет, это слово есть в исключениях
     */
    private void exceptionWord(TokenLight tokenLight) {
        if (tokenLight.isDone()) return;
        exceptionWordRepository.findByWordAndPartOfSpeech(
            tokenLight.getToken().word().toLowerCase(),
            PartOfSpeechEnum.getByTag(tokenLight.getToken().tag())
        ).ifPresent(exceptionWord -> {
                tokenLight.setDone(true);
                tokenLight.setWord(exceptionWord.getWord());
                tokenLight.setTag(exceptionWord.getPartOfSpeech());
            });
    }

    /**
     * Проверяет слово на исключение, без зависимости от части речи
     */
    private void exceptionWordAnyPOS(TokenLight tokenLight) {
        log.debug("exception word any POS, tokenLight: {}", tokenLight);
        if (tokenLight.isDone()) return;
        exceptionWordRepository.findByWordAndPartOfSpeech(
            tokenLight.getToken().word().toLowerCase(),
            PartOfSpeechEnum.ANY
        ).ifPresent(exceptionWord -> {
                tokenLight.setDone(true);
                tokenLight.setWord(exceptionWord.getWord());
                tokenLight.setTag(exceptionWord.getPartOfSpeech());
            });
    }

    /**
     * Применяет правило в зависимости от части речи
     */
    private void applyRuleTransformation(TokenLight tokenLight) {
        if (tokenLight.isDone()) return;
        tokenRuleRepository.findByPartOfSpeech(PartOfSpeechEnum.getByTag(tokenLight.getToken().tag()))
            .ifPresent(tokenRule -> {
                tokenLight.setDone(true);
                tokenRule.getRule().getRule().accept(tokenLight, tokenRule.getTargetPOS());
            });
    }
}
