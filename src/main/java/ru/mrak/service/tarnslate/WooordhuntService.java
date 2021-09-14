package ru.mrak.service.tarnslate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mrak.domain.SoundResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

/**
 * Загрузка озвучки слов с сервиса wooordhunt.ru
 */

@Service
public class WooordhuntService implements GetSoundServiceInterface {

    private final Logger log = LoggerFactory.getLogger(WooordhuntService.class);

    private static final String ADDRESS = "https://wooordhunt.ru";
    private static final String GET_SOUND_ADDRESS = "/data/sound/sow/us";
    private static final String FILE_TYPE = ".mp3";

    @Override
    public Optional<SoundResult> getSound(String word) {
        log.debug("Get sound for word: {}", word);

        Optional<SoundResult> result = Optional.empty();
        try {
            URL url = new URL(ADDRESS + GET_SOUND_ADDRESS + '/' + word + FILE_TYPE);
            URLConnection connection = url.openConnection();
            long contentLength = connection.getContentLengthLong();
            String contentType = connection.getContentType();
            InputStream soundStream = connection.getInputStream();

            SoundResult soundResult = new SoundResult(soundStream, FILE_TYPE, contentLength, contentType);
            result = Optional.of(soundResult);
        } catch (IOException e) {
            log.warn("Con not get sound for word: {}", word);
        }
        return result;
    }

}
