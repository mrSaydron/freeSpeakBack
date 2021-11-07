package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mrak.model.entity.userHasSentences.UserHasSentences;
import ru.mrak.model.entity.userHasSentences.UserHasSentencesId;

@Repository
public interface UserHasSentencesRepository extends JpaRepository<UserHasSentences, UserHasSentencesId> {
}
