package ru.mrak.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

@Getter
@Setter
@AllArgsConstructor
public class SoundResult {
    private InputStream sound;
    private String fileType;
    private Long contentSize;
    private String contentType;
}
