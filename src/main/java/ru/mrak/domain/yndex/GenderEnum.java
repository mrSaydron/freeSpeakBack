package ru.mrak.domain.yndex;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

// todo переделать на нормальные названия родов
@ToString
public enum GenderEnum {
    @JsonProperty("м")
    male,

    @JsonProperty("ж")
    female,

    @JsonProperty("ср")
    middle,

    @JsonProperty("м,ж")
    maleFemale,
}
