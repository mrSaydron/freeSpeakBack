package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mrak.model.entity.UserWordLog;

public interface UserWordLogRepository extends JpaRepository<UserWordLog, Long> {
}
