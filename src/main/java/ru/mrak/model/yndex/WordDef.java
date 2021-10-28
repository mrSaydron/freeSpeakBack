package ru.mrak.model.yndex;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class WordDef {
    private String text;
    private PosEnum pos; // Часть речи
    private String ts; // Транскрипция
    private String fl; // Формы неправильного глагола
    private List<Translate> tr;
}
