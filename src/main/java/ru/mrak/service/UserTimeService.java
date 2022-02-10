package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mrak.config.UserContext;

import java.time.*;

@Service
@RequiredArgsConstructor
public class UserTimeService {

    private final UserContext userContext;

    /**
     * Возвращает локальное время пользователя (без часового пояся)
     */
    public LocalDateTime getLocalTime() {
        ZonedDateTime userTime = userContext.getLocalTime();
        return userTime != null ? userTime.toLocalDateTime() : LocalDateTime.now();
    }

    /**
     * Возвращает время пользователя по Гринвичу
     */
    public Instant getUTCTime() {
        ZonedDateTime userTime = userContext.getLocalTime();
        return userTime != null ? userTime.toInstant() : Instant.now();
    }

    /**
     * Возвращает начало дня для пользователя по его локальному времени (без часового пояся)
     */
    public LocalDateTime getStartDay() {
        ZonedDateTime userTime = userContext.getLocalTime();
        return userTime != null ? userTime.toLocalDate().atStartOfDay() : LocalDate.now().atStartOfDay();
    }

    /**
     * Возвращает часовой пояс пользователя
     */
    public ZoneOffset getZone() {
        ZonedDateTime userTime = userContext.getLocalTime();
        return userTime != null ? userTime.getOffset() : ZoneOffset.ofHours(0);
    }
}
