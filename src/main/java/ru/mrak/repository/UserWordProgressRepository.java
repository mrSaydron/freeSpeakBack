package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.mrak.domain.UserWordProgress;

@SuppressWarnings("unused")
@Repository
public interface UserWordProgressRepository extends JpaRepository<UserWordProgress, Long>, JpaSpecificationExecutor<UserWordProgress> {
}
