package ru.mrak.model.entity.userWordProgress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mrak.model.enumeration.UserWordProgressTypeEnum;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWordProgressId implements Serializable {
    private Long userId;
    private Long wordId;
    private UserWordProgressTypeEnum type;
}
