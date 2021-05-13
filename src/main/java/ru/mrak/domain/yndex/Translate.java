package ru.mrak.domain.yndex;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class Translate {
    private String text;
    private PosEnum pos;
    private String asp;
    private GenderEnum gen;
    private Integer fr;
    private List<Synonym> syn;
    private List<Mean> mean;
    private List<Example> ex;
}
