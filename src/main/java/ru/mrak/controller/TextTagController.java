package ru.mrak.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.mrak.dto.TextTagDto;
import ru.mrak.mapper.TextTagMapper;
import ru.mrak.model.entity.TextTag;
import ru.mrak.service.TextTagService;

import java.util.List;

@RestController
@RequestMapping("/api/text-tag")
@AllArgsConstructor
public class TextTagController {

    private final Logger log = LoggerFactory.getLogger(TextTagController.class);

    private final TextTagMapper textTagMapper;

    private final TextTagService textTagService;

    @PostMapping
    public long save(@RequestBody TextTagDto textTag) {
        log.debug("POST create text tag: {}", textTag);
        return textTagService.save(textTagMapper.toEntity(textTag));
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public TextTagDto get(@PathVariable long id) {
        log.debug("GET text tag by id: {}", id);
        TextTag textTag = textTagService.get(id);
        return textTagMapper.toDto(textTag);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<TextTagDto> getAll() {
        log.debug("GET all text tags");
        List<TextTag> textTags = textTagService.getAll();
        return textTagMapper.toDto(textTags);
    }

    @PutMapping
    public void update(@RequestBody TextTagDto textTag) {
        log.debug("PUT text tag: {}", textTag);
        textTagService.update(textTagMapper.toEntity(textTag));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        log.debug("DELETE text tag, id: {}", id);
        textTagService.delete(id);
    }
}
