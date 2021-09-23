package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.domain.TokenLight;
import ru.mrak.domain.entity.TokenRule;
import ru.mrak.domain.enumeration.TagEnum;
import ru.mrak.repository.TokenRuleRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenRuleService {

    private final TokenRuleRepository tokenRuleRepository;

}
