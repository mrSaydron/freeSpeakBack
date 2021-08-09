package ru.mrak.service.tarnslate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.mrak.domain.SoundResult;

import java.io.InputStream;
import java.util.Optional;

public interface GetSoundServiceInterface {
    Optional<SoundResult> getSound(String word);
}
