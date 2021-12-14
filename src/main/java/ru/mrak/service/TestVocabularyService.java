package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.UserSettings;
import ru.mrak.model.entity.Word;
import ru.mrak.model.entity.testVocabulary.TestVocabulary;
import ru.mrak.model.entity.testVocabulary.TestVocabularyAnswer;
import ru.mrak.model.enumeration.ServiceDataKeysEnum;
import ru.mrak.repository.TestVocabularyAnswerRepository;
import ru.mrak.repository.TestVocabularyRepository;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.dto.testVocabulary.TestVocabularyDto;
import ru.mrak.service.dto.testVocabulary.TestVocabularyResultDto;
import ru.mrak.service.dto.testVocabulary.TestVocabularyWordDto;
import ru.mrak.service.dto.testVocabulary.TestWordResultDto;
import ru.mrak.service.mapper.WordMapper;
import ru.mrak.web.rest.TestVocabularyController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestVocabularyService {

    private final Logger log = LoggerFactory.getLogger(TestVocabularyController.class);

    private final WordMapper wordMapper;

    private final UserService userService;
    private final ServiceDataService serviceDataService;
    private final UserWordService userWordService;
    private final UserSettingsService userSettingsService;

    private final WordRepository wordRepository;
    private final TestVocabularyRepository testVocabularyRepository;
    private final TestVocabularyAnswerRepository testVocabularyAnswerRepository;

    /**
     * Запускает тестерование словарного запаса и возвращает первое слово
     */
    public TestVocabularyDto start() {
        log.debug("Start test vocabulary");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Optional<TestVocabulary> maxTestVocabularyOptional = testVocabularyRepository.findFirstByUserOrderByTryNumberDesc(user);
        int tryNumber = 0;
        if (maxTestVocabularyOptional.isPresent()) {
            tryNumber = maxTestVocabularyOptional.get().getTryNumber() + 1;
        }

        TestVocabulary testVocabulary = new TestVocabulary()
            .setUser(user)
            .setTryDate(Instant.now())
            .setTryNumber(tryNumber);
        testVocabularyRepository.save(testVocabulary);

        int step = getStep(0, true);
        Word word = wordRepository.findStepForwardForVocabulary(step).orElseThrow(RuntimeException::new);

        TestVocabularyWordDto testVocabularyWordDto = new TestVocabularyWordDto();
        testVocabularyWordDto.setTestVocabularyId(testVocabulary.getId());
        testVocabularyWordDto.setWord(wordMapper.toDto(word));
        return testVocabularyWordDto;
    }

    /**
     * Пользователь ответил не верно. Возвращает либо следующее слово, либо результат тестирования
     * @param wordId - идентификатор слова на которое отвечал пользователь
     */
    public TestVocabularyDto answerFail(long wordId, long testVocabularyId) {
        log.debug("Answer fail, wordId: {}, testVocabularyId: {}", wordId, testVocabularyId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        TestVocabulary testVocabulary = testVocabularyRepository.findById(testVocabularyId).orElseThrow(RuntimeException::new);
        TestVocabularyAnswer testVocabularyAnswer = new TestVocabularyAnswer()
            .setSuccess(false)
            .setTestVocabulary(testVocabulary)
            .setWord(wordRepository.getOne(wordId));
        testVocabularyAnswerRepository.save(testVocabularyAnswer);
        testVocabularyAnswerRepository.flush();

        int failAnswersCount =
            testVocabularyAnswerRepository.countAllByTestVocabularyAndSuccessIsFalse(testVocabulary);

        TestVocabularyDto result;
        if (failAnswersCount >= serviceDataService.getIntByKey(ServiceDataKeysEnum.TEST_VOCABULARY_MAX_FAIL_COUNT)) {
            // пользователь сделал много ошибок, расчитываю результат и возвращаю
            long averageAmount = getResult(testVocabulary);
            int vocabulary = wordRepository.countResultWordsForTestVocabulary(averageAmount);
            Word lastKnowWord = wordRepository.findEdgeWordForTestVocabulary(averageAmount)
                .orElseThrow(RuntimeException::new);
            TestWordResultDto testWordResult = new TestWordResultDto()
                .setVocabulary(vocabulary);
            TestVocabularyResultDto testVocabularyResultDto = new TestVocabularyResultDto();
            testVocabularyResultDto.setTestVocabularyId(testVocabularyId);
            testVocabularyResultDto.setResult(testWordResult);

            result = testVocabularyResultDto;
            testVocabulary
                .setResultCount(vocabulary)
                .setResultWord(lastKnowWord);

            // todo асинхронно
            saveResult(averageAmount, user);
        } else {
            // ошибок еще мало, следующее слово
            int step = getStep(failAnswersCount, false);
            Optional<Word> stepBackOptional = wordRepository.findStepBackForVocabulary(wordId, step, testVocabularyId);
            if (!stepBackOptional.isPresent()) {
                stepBackOptional = wordRepository.findLastBackForVocabulary(wordId, testVocabularyId);
                if (!stepBackOptional.isPresent()) {
                    stepBackOptional = wordRepository.findNextForVocabulary(wordId, testVocabularyId);
                }
            }
            Word nextWord = stepBackOptional.orElseThrow(RuntimeException::new);
            TestVocabularyWordDto testVocabularyWordDto = new TestVocabularyWordDto();
            testVocabularyWordDto.setTestVocabularyId(testVocabularyId);
            testVocabularyWordDto.setWord(wordMapper.toDto(nextWord));

            result = testVocabularyWordDto;
        }

        log.debug("result, {}", result);
        return result;
    }

    /**
     * Пользователь ответил не верно. Возвращает либо следующее слово, либо результат тестирования
     * @param wordId - идентификатор слова на которое отвечал пользователь
     */
    public TestVocabularyDto answerSuccess(long wordId, long testVocabularyId) {
        log.debug("Answer success, wordId: {}, testVocabularyId: {}", wordId, testVocabularyId);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);
        TestVocabulary testVocabulary = testVocabularyRepository.findById(testVocabularyId).orElseThrow(RuntimeException::new);

        TestVocabularyAnswer testVocabularyAnswer = new TestVocabularyAnswer()
            .setSuccess(true)
            .setTestVocabulary(testVocabulary)
            .setWord(wordRepository.getOne(wordId));
        testVocabularyAnswerRepository.save(testVocabularyAnswer);
        testVocabularyAnswerRepository.flush();

        TestVocabularyDto result;
        int failAnswersCount =
            testVocabularyAnswerRepository.countAllByTestVocabularyAndSuccessIsFalse(testVocabulary);
        int step = getStep(failAnswersCount, false);
        Optional<Word> nextWordOptional = wordRepository.findStepForwardForVocabulary(wordId, step, testVocabularyId);
        if (!nextWordOptional.isPresent()) {
            nextWordOptional = wordRepository.findLastForwardForVocabulary(wordId, testVocabularyId);
        }
        if (nextWordOptional.isPresent()) {
            // Слудующее слово есть, возвращаем следующее слово
            TestVocabularyWordDto testVocabularyWordDto = new TestVocabularyWordDto();
            testVocabularyWordDto.setTestVocabularyId(testVocabularyId);
            testVocabularyWordDto.setWord(wordMapper.toDto(nextWordOptional.get()));
            result = testVocabularyWordDto;
        } else {
            // Слова закончились, возвращаем результат
            int vocabulary = wordRepository.countAllByTranslateNotNull();
            Word lastKnowWord = wordRepository.findFirstByTranslateIsNotNullOrderByTotalAmountDesc().orElseThrow(RuntimeException::new);

            testVocabulary
                .setResultCount(vocabulary)
                .setResultWord(lastKnowWord);

            TestWordResultDto testWordResultDto = new TestWordResultDto()
                .setVocabulary(vocabulary);

            TestVocabularyResultDto testVocabularyResultDto = new TestVocabularyResultDto();
            testVocabularyResultDto.setTestVocabularyId(testVocabularyId);
            testVocabularyResultDto.setResult(testWordResultDto);
            result = testVocabularyResultDto;

            // todo асинхронно
            saveResult(lastKnowWord.getTotalAmount(), user);
        }

        log.debug("result, {}", result);
        return result;
    }

    /**
     * Обновляет словарь пользователя согласно результатам тестирования
     */
    private void saveResult(long averageAmount, User user) {
        log.debug("save testing result, averageAmount: {}, userId: {}", averageAmount, user.getId());

        List<Word> resultWords = wordRepository.findResultWordsForTestVocabulary(averageAmount, user.getId());
        userWordService.knowWordsByTest(resultWords);

        UserSettings userSettings = userSettingsService.get();
        userSettings.setTestedVocabulary(true);
    }

    /**
     * Возвращает результат тестирования в виде частоты слова (totalAmount)
     */
    private long getResult(TestVocabulary testVocabulary) {
        log.debug("get result");
        List<TestVocabularyAnswer> failAnswers = testVocabularyAnswerRepository.findAllByTestVocabularyAndSuccessIsFalse(testVocabulary);
        return (long) failAnswers.stream()
            .map(TestVocabularyAnswer::getWord)
            .mapToLong(Word::getTotalAmount)
            .average()
            .orElseThrow(RuntimeException::new);
    }

    /**
     * Возвращает размер следующего шага для поиска следующего слова
     */
    private int getStep(int failAnswerCount, boolean first) {
        log.debug("get step");
        int result;
        if (first) {
            result = serviceDataService.getIntByKey(ServiceDataKeysEnum.TEST_VOCABULARY_FIRST_STEP_SIZE) +
                getRandomStep();
        } else {
            int step = serviceDataService.getIntByKey(ServiceDataKeysEnum.TEST_VOCABULARY_BASE_STEP_SIZE) / (1 + failAnswerCount);
            result = step + getRandomStep();
        }
        return result;
    }

    /**
     * Возвращает случайную добавку для шага
     */
    private int getRandomStep() {
        int randomSize = serviceDataService.getIntByKey(ServiceDataKeysEnum.TEST_VOCABULARY_RANDOM_SIZE);
        return (int) (Math.random() * randomSize);
    }
}
