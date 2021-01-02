package ru.mrak.web.rest;

import ru.mrak.LibFourApp;
import ru.mrak.domain.Dictionary;
import ru.mrak.repository.DictionaryRepository;
import ru.mrak.service.DictionaryService;
import ru.mrak.service.dto.DictionaryDTO;
import ru.mrak.service.mapper.DictionaryMapper;

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
 * Integration tests for the {@link DictionaryResource} REST controller.
 */
@SpringBootTest(classes = LibFourApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DictionaryResourceIT {

    private static final String DEFAULT_BASE_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_BASE_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TARGER_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_TARGER_LANGUAGE = "BBBBBBBBBB";

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDictionaryMockMvc;

    private Dictionary dictionary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dictionary createEntity(EntityManager em) {
        Dictionary dictionary = new Dictionary()
            .baseLanguage(DEFAULT_BASE_LANGUAGE)
            .targerLanguage(DEFAULT_TARGER_LANGUAGE);
        return dictionary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dictionary createUpdatedEntity(EntityManager em) {
        Dictionary dictionary = new Dictionary()
            .baseLanguage(UPDATED_BASE_LANGUAGE)
            .targerLanguage(UPDATED_TARGER_LANGUAGE);
        return dictionary;
    }

    @BeforeEach
    public void initTest() {
        dictionary = createEntity(em);
    }

    @Test
    @Transactional
    public void createDictionary() throws Exception {
        int databaseSizeBeforeCreate = dictionaryRepository.findAll().size();
        // Create the Dictionary
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);
        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO)))
            .andExpect(status().isCreated());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeCreate + 1);
        Dictionary testDictionary = dictionaryList.get(dictionaryList.size() - 1);
        assertThat(testDictionary.getBaseLanguage()).isEqualTo(DEFAULT_BASE_LANGUAGE);
        assertThat(testDictionary.getTargerLanguage()).isEqualTo(DEFAULT_TARGER_LANGUAGE);
    }

    @Test
    @Transactional
    public void createDictionaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dictionaryRepository.findAll().size();

        // Create the Dictionary with an existing ID
        dictionary.setId(1L);
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDictionaryMockMvc.perform(post("/api/dictionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDictionaries() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        // Get all the dictionaryList
        restDictionaryMockMvc.perform(get("/api/dictionaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dictionary.getId().intValue())))
            .andExpect(jsonPath("$.[*].baseLanguage").value(hasItem(DEFAULT_BASE_LANGUAGE)))
            .andExpect(jsonPath("$.[*].targerLanguage").value(hasItem(DEFAULT_TARGER_LANGUAGE)));
    }
    
    @Test
    @Transactional
    public void getDictionary() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        // Get the dictionary
        restDictionaryMockMvc.perform(get("/api/dictionaries/{id}", dictionary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dictionary.getId().intValue()))
            .andExpect(jsonPath("$.baseLanguage").value(DEFAULT_BASE_LANGUAGE))
            .andExpect(jsonPath("$.targerLanguage").value(DEFAULT_TARGER_LANGUAGE));
    }
    @Test
    @Transactional
    public void getNonExistingDictionary() throws Exception {
        // Get the dictionary
        restDictionaryMockMvc.perform(get("/api/dictionaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDictionary() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();

        // Update the dictionary
        Dictionary updatedDictionary = dictionaryRepository.findById(dictionary.getId()).get();
        // Disconnect from session so that the updates on updatedDictionary are not directly saved in db
        em.detach(updatedDictionary);
        updatedDictionary
            .baseLanguage(UPDATED_BASE_LANGUAGE)
            .targerLanguage(UPDATED_TARGER_LANGUAGE);
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(updatedDictionary);

        restDictionaryMockMvc.perform(put("/api/dictionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO)))
            .andExpect(status().isOk());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
        Dictionary testDictionary = dictionaryList.get(dictionaryList.size() - 1);
        assertThat(testDictionary.getBaseLanguage()).isEqualTo(UPDATED_BASE_LANGUAGE);
        assertThat(testDictionary.getTargerLanguage()).isEqualTo(UPDATED_TARGER_LANGUAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingDictionary() throws Exception {
        int databaseSizeBeforeUpdate = dictionaryRepository.findAll().size();

        // Create the Dictionary
        DictionaryDTO dictionaryDTO = dictionaryMapper.toDto(dictionary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDictionaryMockMvc.perform(put("/api/dictionaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dictionaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dictionary in the database
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDictionary() throws Exception {
        // Initialize the database
        dictionaryRepository.saveAndFlush(dictionary);

        int databaseSizeBeforeDelete = dictionaryRepository.findAll().size();

        // Delete the dictionary
        restDictionaryMockMvc.perform(delete("/api/dictionaries/{id}", dictionary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dictionary> dictionaryList = dictionaryRepository.findAll();
        assertThat(dictionaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
