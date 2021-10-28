package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mrak.model.entity.TokenRule;
import ru.mrak.model.enumeration.PartOfSpeechEnum;

import java.util.Optional;

@Repository
public interface TokenRuleRepository extends JpaRepository<TokenRule, Long> {
    Optional<TokenRule> findByPartOfSpeech(PartOfSpeechEnum partOfSpeech);
}
