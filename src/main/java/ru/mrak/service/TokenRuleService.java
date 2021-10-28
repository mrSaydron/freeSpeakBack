package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.repository.TokenRuleRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenRuleService {

    private final TokenRuleRepository tokenRuleRepository;

}
