package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mrak.domain.entity.TokenRule;
import ru.mrak.domain.enumeration.TagEnum;

import java.util.Optional;

@Repository
public interface TokenRuleRepository extends JpaRepository<TokenRule, Long> {
    Optional<TokenRule> findByPartOfSpeech(TagEnum partOfSpeech);
}
