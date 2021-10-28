package ru.mrak.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ru.mrak.config.Constants;
import ru.mrak.model.enumeration.PartOfSpeechEnum;
import ru.mrak.model.enumeration.TokenRuleEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "token_rule")
@Getter
@Setter
@ToString
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TokenRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_rule_seq")
    @SequenceGenerator(name = "token_rule_seq", sequenceName = "token_rule_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "part_of_speech")
    @Enumerated(EnumType.STRING)
    private PartOfSpeechEnum partOfSpeech;

    @NotNull
    @Column(name = "rule_name")
    @Enumerated(EnumType.STRING)
    private TokenRuleEnum rule;

    @NotNull
    @Column(name = "target_part_of_speech")
    @Enumerated(EnumType.STRING)
    private PartOfSpeechEnum targetPOS;
}
