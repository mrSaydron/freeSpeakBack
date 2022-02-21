package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mrak.model.entity.TextTag;

import java.util.Optional;

public interface TextTagRepository extends JpaRepository<TextTag, Long> {
    Optional<TextTag> findByTagNameAndTypeName(String tagName, String typeName);
}
