package ru.mrak.service.tarnslate;

import ru.mrak.model.SoundResult;

import java.util.Optional;

public interface GetSoundServiceInterface {
    Optional<SoundResult> getSound(String word);
}
