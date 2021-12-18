package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.UserWordLog;
import ru.mrak.model.entity.Word;
import ru.mrak.model.entity.testVocabulary.TestVocabulary;
import ru.mrak.model.enumeration.UserWordLogTypeEnum;
import ru.mrak.repository.UserWordLogRepository;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserWordLogService {

    private final Logger log = LoggerFactory.getLogger(UserWordLogService.class);

    private final UserService userService;

    private final UserWordLogRepository userWordLogRepository;

    public void create(Word word, UserWordLogTypeEnum type) {
        log.debug("create, type: {}, word: {}", type, word);
        create(word, type, null);
    }

    public void create(List<Word> words, UserWordLogTypeEnum type) {
        log.debug("create, type: {}", type);
        create(words, type, null);
    }

    public void create(List<Word> words, UserWordLogTypeEnum type, TestVocabulary testVocabulary) {
        log.debug("create, type: {}", type);
        words.forEach(word -> create(word, type, null));
    }

    public void create(Word word, UserWordLogTypeEnum type, TestVocabulary testVocabulary) {
        log.debug("create, type: {}, word: {}, testVocabulary: {}", type, word, testVocabulary);
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        UserWordLog userWordLog = new UserWordLog();
        userWordLog.setType(type);
        userWordLog.setUser(user);
        userWordLog.setWord(word);
        userWordLog.setDate(Instant.now());

        userWordLogRepository.save(userWordLog);
    }

}
