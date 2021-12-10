package ru.mrak.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "user_settings")
@Getter
@Setter
@Accessors(chain = true)
public class UserSettings {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tested_vocabulary")
    private boolean testedVocabulary;

}
