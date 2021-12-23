package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mrak.model.entity.TextTag;

public interface TextTagRepository extends JpaRepository<TextTag, Long> {
}
