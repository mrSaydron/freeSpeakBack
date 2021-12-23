package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.TextTag;
import ru.mrak.repository.TextTagRepository;

import java.util.List;

/**
 * Сервис для работы с тегами для книг
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TextTagService {

    private final Logger log = LoggerFactory.getLogger(TextTagService.class);

    private final TextTagRepository textTagRepository;

    public long save(TextTag textTag) {
        log.debug("create text tag");
        textTagRepository.save(textTag);
        textTagRepository.flush();

        return textTag.getId();
    }

    @Transactional(readOnly = true)
    public TextTag get(long id) {
        log.debug("get text tag, id: {}", id);
        return textTagRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    public List<TextTag> getAll() {
        log.debug("get all text tags");
        return textTagRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TextTag> getAllByIds(List<Long> ids) {
        log.debug("get all by ids: {}", ids);
        return textTagRepository.findAllById(ids);
    }

    public void update(TextTag textTag) {
        log.debug("update text tag");
        textTagRepository.save(textTag);
    }

    public void delete(long id) {
        log.debug("delete text tag, id: {}", id);
        textTagRepository.deleteById(id);
    }
}
