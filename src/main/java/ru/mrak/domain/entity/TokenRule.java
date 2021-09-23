package ru.mrak.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import ru.mrak.domain.enumeration.TagEnum;
import ru.mrak.domain.enumeration.TokenRuleEnum;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "part_of_speech")
    @Enumerated(EnumType.STRING)
    private TagEnum partOfSpeech;

    @NotNull
    @Column(name = "rule_name")
    @Enumerated(EnumType.STRING)
    private TokenRuleEnum rule;

}
