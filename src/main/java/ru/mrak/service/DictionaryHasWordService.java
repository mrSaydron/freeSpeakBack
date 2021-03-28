package ru.mrak.service;

import ru.mrak.domain.BookDictionaryHasWord;
import ru.mrak.repository.DictionaryHasWordRepository;
import ru.mrak.service.dto.DictionaryHasWordDTO;
import ru.mrak.service.mapper.DictionaryHasWordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BookDictionaryHasWord}.
 */
@Service
@Transactional
public class DictionaryHasWordService {

    private final Logger log = LoggerFactory.getLogger(DictionaryHasWordService.class);

    private final DictionaryHasWordRepository dictionaryHasWordRepository;

    private final DictionaryHasWordMapper dictionaryHasWordMapper;

    public DictionaryHasWordService(DictionaryHasWordRepository dictionaryHasWordRepository, DictionaryHasWordMapper dictionaryHasWordMapper) {
        this.dictionaryHasWordRepository = dictionaryHasWordRepository;
        this.dictionaryHasWordMapper = dictionaryHasWordMapper;
    }

    /**
     * Save a dictionaryHasWord.
     *
     * @param dictionaryHasWordDTO the entity to save.
     * @return the persisted entity.
     */
    public DictionaryHasWordDTO save(DictionaryHasWordDTO dictionaryHasWordDTO) {
        log.debug("Request to save DictionaryHasWord : {}", dictionaryHasWordDTO);
        BookDictionaryHasWord dictionaryHasWord = dictionaryHasWordMapper.toEntity(dictionaryHasWordDTO);
        dictionaryHasWord = dictionaryHasWordRepository.save(dictionaryHasWord);
        return dictionaryHasWordMapper.toDto(dictionaryHasWord);
    }

    /**
     * Get all the dictionaryHasWords.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DictionaryHasWordDTO> findAll() {
        log.debug("Request to get all DictionaryHasWords");
        return dictionaryHasWordRepository.findAll().stream()
            .map(dictionaryHasWordMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one dictionaryHasWord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DictionaryHasWordDTO> findOne(Long id) {
        log.debug("Request to get DictionaryHasWord : {}", id);
        return dictionaryHasWordRepository.findById(id)
            .map(dictionaryHasWordMapper::toDto);
    }

    /**
     * Delete the dictionaryHasWord by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DictionaryHasWord : {}", id);
        dictionaryHasWordRepository.deleteById(id);
    }
}
