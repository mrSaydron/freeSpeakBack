package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.TextTag;
import ru.mrak.repository.TextTagRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        saveCheck(textTag);
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
        return textTagRepository.findAll().stream()
            .sorted(Comparator.comparing(TextTag::getId))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TextTag> getAllByIds(List<Long> ids) {
        log.debug("get all by ids: {}", ids);
        return textTagRepository.findAllById(ids);
    }

    public void update(TextTag textTag) {
        log.debug("update text tag");
        updateCheck(textTag);
        textTagRepository.save(textTag);
    }

    public void delete(long id) {
        log.debug("delete text tag, id: {}", id);
        textTagRepository.deleteById(id);
    }

    private void saveCheck(TextTag textTag) {
        checkParentId(textTag);
        checkHasParent(textTag);
        checkNameAndType(textTag);
    }

    private void updateCheck(TextTag textTag) {
        if (textTag.getId() == null) {
            throw new RuntimeException("Нет идентификатора тэга");
        }
        checkParentId(textTag);
        checkHasParent(textTag);
        checkNameAndType(textTag);
    }

    /**
     * Проверка указан ли идентификатор для родительского тега
     */
    private void checkParentId(TextTag textTag) {
        if (textTag.getParent() != null && textTag.getParent().getId() == null) {
            throw new RuntimeException("Нет идентификатора у родительского тега");
        }
    }

    /**
     * Проверка существует ли родительский тег с переданым идентификатором
     */
    private void checkHasParent(TextTag textTag) {
        if (textTag.getParent() != null && textTag.getParent().getId() != null) {
            textTagRepository.findById(textTag.getParent().getId())
                .orElseThrow(() -> new RuntimeException("Не найден родительский тег"));
        }
    }

    /**
     * Проверка на то, есть ли тег с такими же именем и типом. За исключением того же самого тэга
     */
    private void checkNameAndType(TextTag textTag) {
        if (textTag.getTagName() != null && textTag.getTypeName() != null) {
            textTagRepository.findByTagNameAndTypeName(
                textTag.getTagName(),
                textTag.getTypeName()
            ).ifPresent(tag -> {
                if (!Objects.equals(tag.getId(), textTag.getId())) {
                    throw new RuntimeException("Тег с таким именем и типом уже существует");
                }
            });
        }
    }
}
