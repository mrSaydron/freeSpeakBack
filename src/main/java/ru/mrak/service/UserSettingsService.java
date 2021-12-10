package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.UserSettings;
import ru.mrak.repository.UserSettingsRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSettingsService {

    private final Logger log = LoggerFactory.getLogger(UserSettingsService.class);

    private final UserService userService;

    private final UserSettingsRepository userSettingsRepository;

    /**
     * Возвращает настройки пользователя. Если настроек нет, создает настройки по умолчанию
     */
    public UserSettings get() {
        log.debug("get user settings");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        Optional<UserSettings> userSettings = userSettingsRepository.getByUserId(user.getId());
        return userSettings.orElseGet(this::create);
    }

    /**
     * Создает настройки пользователя
     */
    public UserSettings create() {
        log.debug("create user settings");
        User user = userService.getUserWithAuthorities().orElseThrow(RuntimeException::new);

        UserSettings userSettings = new UserSettings()
            .setUserId(user.getId())
            .setTestedVocabulary(false);
        userSettingsRepository.save(userSettings);

        return userSettings;
    }

}
