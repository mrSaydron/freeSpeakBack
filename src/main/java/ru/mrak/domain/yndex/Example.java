package ru.mrak.domain.yndex;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Example {
    private String text;
    private List<ExampleTranslate> tr;
}
