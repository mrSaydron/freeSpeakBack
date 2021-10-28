package ru.mrak.model.yndex;

import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WordHead {
    private Map<String, Object> head;
    private List<WordDef> def;
}
