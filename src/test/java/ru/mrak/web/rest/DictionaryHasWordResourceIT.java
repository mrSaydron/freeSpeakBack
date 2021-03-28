package ru.mrak.web.rest;

import ru.mrak.LibFourApp;
import ru.mrak.domain.BookDictionaryHasWord;
import ru.mrak.repository.DictionaryHasWordRepository;
import ru.mrak.service.DictionaryHasWordService;
import ru.mrak.service.dto.DictionaryHasWordDTO;
import ru.mrak.service.mapper.DictionaryHasWordMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DictionaryHasWordResource} REST controller.
 */
@SpringBootTest(classes = LibFourApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DictionaryHasWordResourceIT {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    @Autowired
    private DictionaryHasWordRepository dictionaryHasWordRepository;

    @Autowired
    private DictionaryHasWordMapper dictionaryHasWordMapper;

    @Autowired
    private DictionaryHasWordService dictionaryHasWordService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDictionaryHasWordMockMvc;

    private BookDictionaryHasWord dictionaryHasWord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookDictionaryHasWord createEntity(EntityManager em) {
        BookDictionaryHasWord dictionaryHasWord = new BookDictionaryHasWord()
            .count(DEFAULT_COUNT);
        return dictionaryHasWord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookDictionaryHasWord createUpdatedEntity(EntityManager em) {
        BookDictionaryHasWord dictionaryHasWord = new BookDictionaryHasWord()
            .count(UPDATED_COUNT);
        return dictionaryHasWord;
    }

    @BeforeEach
    public void initTest() {
        dictionaryHasWord = createEntity(em);
    }

    @Test
    @Transactional
    public void createDictionaryHasWord() throws Exception {
        int databaseSizeBeforeCreate = dictionaryHasWordRepository.findAll().size();
        // Create the DictionaryHasWord
        DictionaryHasWordDTO dictionaryHasWordDTO = dictionaryHasWordMapper.toDto(dictionaryHasWord);
        restDictionaryHasWordMockMvc.perform(post("/api/dictionary-has-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryHasWordDTO)))
            .andExpect(status().isCreated());

        // Validate the DictionaryHasWord in the database
        List<BookDictionaryHasWord> dictionaryHasWordList = dictionaryHasWordRepository.findAll();
        assertThat(dictionaryHasWordList).hasSize(databaseSizeBeforeCreate + 1);
        BookDictionaryHasWord testDictionaryHasWord = dictionaryHasWordList.get(dictionaryHasWordList.size() - 1);
        assertThat(testDictionaryHasWord.getCount()).isEqualTo(DEFAULT_COUNT);
    }

    @Test
    @Transactional
    public void createDictionaryHasWordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dictionaryHasWordRepository.findAll().size();

        // Create the DictionaryHasWord with an existing ID
        dictionaryHasWord.setId(1L);
        DictionaryHasWordDTO dictionaryHasWordDTO = dictionaryHasWordMapper.toDto(dictionaryHasWord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDictionaryHasWordMockMvc.perform(post("/api/dictionary-has-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryHasWordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DictionaryHasWord in the database
        List<BookDictionaryHasWord> dictionaryHasWordList = dictionaryHasWordRepository.findAll();
        assertThat(dictionaryHasWordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDictionaryHasWords() throws Exception {
        // Initialize the database
        dictionaryHasWordRepository.saveAndFlush(dictionaryHasWord);

        // Get all the dictionaryHasWordList
        restDictionaryHasWordMockMvc.perform(get("/api/dictionary-has-words?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictionaryHasWord.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)));
    }

    @Test
    @Transactional
    public void getDictionaryHasWord() throws Exception {
        // Initialize the database
        dictionaryHasWordRepository.saveAndFlush(dictionaryHasWord);

        // Get the dictionaryHasWord
        restDictionaryHasWordMockMvc.perform(get("/api/dictionary-has-words/{id}", dictionaryHasWord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dictionaryHasWord.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT));
    }
    @Test
    @Transactional
    public void getNonExistingDictionaryHasWord() throws Exception {
        // Get the dictionaryHasWord
        restDictionaryHasWordMockMvc.perform(get("/api/dictionary-has-words/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDictionaryHasWord() throws Exception {
        // Initialize the database
        dictionaryHasWordRepository.saveAndFlush(dictionaryHasWord);

        int databaseSizeBeforeUpdate = dictionaryHasWordRepository.findAll().size();

        // Update the dictionaryHasWord
        BookDictionaryHasWord updatedDictionaryHasWord = dictionaryHasWordRepository.findById(dictionaryHasWord.getId()).get();
        // Disconnect from session so that the updates on updatedDictionaryHasWord are not directly saved in db
        em.detach(updatedDictionaryHasWord);
        updatedDictionaryHasWord
            .count(UPDATED_COUNT);
        DictionaryHasWordDTO dictionaryHasWordDTO = dictionaryHasWordMapper.toDto(updatedDictionaryHasWord);

        restDictionaryHasWordMockMvc.perform(put("/api/dictionary-has-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryHasWordDTO)))
            .andExpect(status().isOk());

        // Validate the DictionaryHasWord in the database
        List<BookDictionaryHasWord> dictionaryHasWordList = dictionaryHasWordRepository.findAll();
        assertThat(dictionaryHasWordList).hasSize(databaseSizeBeforeUpdate);
        BookDictionaryHasWord testDictionaryHasWord = dictionaryHasWordList.get(dictionaryHasWordList.size() - 1);
        assertThat(testDictionaryHasWord.getCount()).isEqualTo(UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingDictionaryHasWord() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryHasWordRepository.findAll().size();

        // Create the DictionaryHasWord
        DictionaryHasWordDTO dictionaryHasWordDTO = dictionaryHasWordMapper.toDto(dictionaryHasWord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDictionaryHasWordMockMvc.perform(put("/api/dictionary-has-words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryHasWordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DictionaryHasWord in the database
        List<BookDictionaryHasWord> dictionaryHasWordList = dictionaryHasWordRepository.findAll();
        assertThat(dictionaryHasWordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDictionaryHasWord() throws Exception {
        // Initialize the database
        dictionaryHasWordRepository.saveAndFlush(dictionaryHasWord);

        int databaseSizeBeforeDelete = dictionaryHasWordRepository.findAll().size();

        // Delete the dictionaryHasWord
        restDictionaryHasWordMockMvc.perform(delete("/api/dictionary-has-words/{id}", dictionaryHasWord.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookDictionaryHasWord> dictionaryHasWordList = dictionaryHasWordRepository.findAll();
        assertThat(dictionaryHasWordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
