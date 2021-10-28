package ru.mrak.model.yndex;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Synonym {
    private String text;
    private PosEnum pos;
    private String asp;
    private Integer fr;
}
