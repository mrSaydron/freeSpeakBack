package ru.mrak.web.rest;

import ru.mrak.LibFourApp;
import ru.mrak.domain.Word;
import ru.mrak.repository.WordRepository;
import ru.mrak.service.WordService;
import ru.mrak.service.dto.WordDTO;
import ru.mrak.service.mapper.WordMapper;
import ru.mrak.service.dto.WordCriteria;
import ru.mrak.service.WordQueryService;

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
 * Integration tests for the {@link WordResource} REST controller.
 */
@SpringBootTest(classes = LibFourApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WordResourceIT {

    private static final String DEFAULT_WORD = "AAAAAAAAAA";
    private static final String UPDATED_WORD = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSLATE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATE = "BBBBBBBBBB";

    private static final String DEFAULT_PART_OF_SPEECH = "AAAAAAAAAA";
    private static final String UPDATED_PART_OF_SPEECH = "BBBBBBBBBB";

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordMapper wordMapper;

    @Autowired
    private WordService wordService;

    @Autowired
    private WordQueryService wordQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWordMockMvc;

    private Word word;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Word createEntity(EntityManager em) {
        Word word = new Word()
            .word(DEFAULT_WORD)
            .translate(DEFAULT_TRANSLATE)
            .partOfSpeech(DEFAULT_PART_OF_SPEECH);
        return word;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Word createUpdatedEntity(EntityManager em) {
        Word word = new Word()
            .word(UPDATED_WORD)
            .translate(UPDATED_TRANSLATE)
            .partOfSpeech(UPDATED_PART_OF_SPEECH);
        return word;
    }

    @BeforeEach
    public void initTest() {
        word = createEntity(em);
    }

    @Test
    @Transactional
    public void createWord() throws Exception {
        int databaseSizeBeforeCreate = wordRepository.findAll().size();
        // Create the Word
        WordDTO wordDTO = wordMapper.toDto(word);
        restWordMockMvc.perform(post("/api/words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isCreated());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeCreate + 1);
        Word testWord = wordList.get(wordList.size() - 1);
        assertThat(testWord.getWord()).isEqualTo(DEFAULT_WORD);
        assertThat(testWord.getTranslate()).isEqualTo(DEFAULT_TRANSLATE);
        assertThat(testWord.getPartOfSpeech()).isEqualTo(DEFAULT_PART_OF_SPEECH);
    }

    @Test
    @Transactional
    public void createWordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wordRepository.findAll().size();

        // Create the Word with an existing ID
        word.setId(1L);
        WordDTO wordDTO = wordMapper.toDto(word);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWordMockMvc.perform(post("/api/words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWordIsRequired() throws Exception {
        int databaseSizeBeforeTest = wordRepository.findAll().size();
        // set the field null
        word.setWord(null);

        // Create the Word, which fails.
        WordDTO wordDTO = wordMapper.toDto(word);


        restWordMockMvc.perform(post("/api/words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isBadRequest());

        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWords() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList
        restWordMockMvc.perform(get("/api/words?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(word.getId().intValue())))
            .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD)))
            .andExpect(jsonPath("$.[*].translate").value(hasItem(DEFAULT_TRANSLATE)))
            .andExpect(jsonPath("$.[*].partOfSpeech").value(hasItem(DEFAULT_PART_OF_SPEECH)));
    }
    
    @Test
    @Transactional
    public void getWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get the word
        restWordMockMvc.perform(get("/api/words/{id}", word.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(word.getId().intValue()))
            .andExpect(jsonPath("$.word").value(DEFAULT_WORD))
            .andExpect(jsonPath("$.translate").value(DEFAULT_TRANSLATE))
            .andExpect(jsonPath("$.partOfSpeech").value(DEFAULT_PART_OF_SPEECH));
    }


    @Test
    @Transactional
    public void getWordsByIdFiltering() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        Long id = word.getId();

        defaultWordShouldBeFound("id.equals=" + id);
        defaultWordShouldNotBeFound("id.notEquals=" + id);

        defaultWordShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWordShouldNotBeFound("id.greaterThan=" + id);

        defaultWordShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWordShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWordsByWordIsEqualToSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where word equals to DEFAULT_WORD
        defaultWordShouldBeFound("word.equals=" + DEFAULT_WORD);

        // Get all the wordList where word equals to UPDATED_WORD
        defaultWordShouldNotBeFound("word.equals=" + UPDATED_WORD);
    }

    @Test
    @Transactional
    public void getAllWordsByWordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where word not equals to DEFAULT_WORD
        defaultWordShouldNotBeFound("word.notEquals=" + DEFAULT_WORD);

        // Get all the wordList where word not equals to UPDATED_WORD
        defaultWordShouldBeFound("word.notEquals=" + UPDATED_WORD);
    }

    @Test
    @Transactional
    public void getAllWordsByWordIsInShouldWork() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where word in DEFAULT_WORD or UPDATED_WORD
        defaultWordShouldBeFound("word.in=" + DEFAULT_WORD + "," + UPDATED_WORD);

        // Get all the wordList where word equals to UPDATED_WORD
        defaultWordShouldNotBeFound("word.in=" + UPDATED_WORD);
    }

    @Test
    @Transactional
    public void getAllWordsByWordIsNullOrNotNull() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where word is not null
        defaultWordShouldBeFound("word.specified=true");

        // Get all the wordList where word is null
        defaultWordShouldNotBeFound("word.specified=false");
    }
                @Test
    @Transactional
    public void getAllWordsByWordContainsSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where word contains DEFAULT_WORD
        defaultWordShouldBeFound("word.contains=" + DEFAULT_WORD);

        // Get all the wordList where word contains UPDATED_WORD
        defaultWordShouldNotBeFound("word.contains=" + UPDATED_WORD);
    }

    @Test
    @Transactional
    public void getAllWordsByWordNotContainsSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where word does not contain DEFAULT_WORD
        defaultWordShouldNotBeFound("word.doesNotContain=" + DEFAULT_WORD);

        // Get all the wordList where word does not contain UPDATED_WORD
        defaultWordShouldBeFound("word.doesNotContain=" + UPDATED_WORD);
    }


    @Test
    @Transactional
    public void getAllWordsByTranslateIsEqualToSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where translate equals to DEFAULT_TRANSLATE
        defaultWordShouldBeFound("translate.equals=" + DEFAULT_TRANSLATE);

        // Get all the wordList where translate equals to UPDATED_TRANSLATE
        defaultWordShouldNotBeFound("translate.equals=" + UPDATED_TRANSLATE);
    }

    @Test
    @Transactional
    public void getAllWordsByTranslateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where translate not equals to DEFAULT_TRANSLATE
        defaultWordShouldNotBeFound("translate.notEquals=" + DEFAULT_TRANSLATE);

        // Get all the wordList where translate not equals to UPDATED_TRANSLATE
        defaultWordShouldBeFound("translate.notEquals=" + UPDATED_TRANSLATE);
    }

    @Test
    @Transactional
    public void getAllWordsByTranslateIsInShouldWork() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where translate in DEFAULT_TRANSLATE or UPDATED_TRANSLATE
        defaultWordShouldBeFound("translate.in=" + DEFAULT_TRANSLATE + "," + UPDATED_TRANSLATE);

        // Get all the wordList where translate equals to UPDATED_TRANSLATE
        defaultWordShouldNotBeFound("translate.in=" + UPDATED_TRANSLATE);
    }

    @Test
    @Transactional
    public void getAllWordsByTranslateIsNullOrNotNull() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where translate is not null
        defaultWordShouldBeFound("translate.specified=true");

        // Get all the wordList where translate is null
        defaultWordShouldNotBeFound("translate.specified=false");
    }
                @Test
    @Transactional
    public void getAllWordsByTranslateContainsSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where translate contains DEFAULT_TRANSLATE
        defaultWordShouldBeFound("translate.contains=" + DEFAULT_TRANSLATE);

        // Get all the wordList where translate contains UPDATED_TRANSLATE
        defaultWordShouldNotBeFound("translate.contains=" + UPDATED_TRANSLATE);
    }

    @Test
    @Transactional
    public void getAllWordsByTranslateNotContainsSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where translate does not contain DEFAULT_TRANSLATE
        defaultWordShouldNotBeFound("translate.doesNotContain=" + DEFAULT_TRANSLATE);

        // Get all the wordList where translate does not contain UPDATED_TRANSLATE
        defaultWordShouldBeFound("translate.doesNotContain=" + UPDATED_TRANSLATE);
    }


    @Test
    @Transactional
    public void getAllWordsByPartOfSpeechIsEqualToSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where partOfSpeech equals to DEFAULT_PART_OF_SPEECH
        defaultWordShouldBeFound("partOfSpeech.equals=" + DEFAULT_PART_OF_SPEECH);

        // Get all the wordList where partOfSpeech equals to UPDATED_PART_OF_SPEECH
        defaultWordShouldNotBeFound("partOfSpeech.equals=" + UPDATED_PART_OF_SPEECH);
    }

    @Test
    @Transactional
    public void getAllWordsByPartOfSpeechIsNotEqualToSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where partOfSpeech not equals to DEFAULT_PART_OF_SPEECH
        defaultWordShouldNotBeFound("partOfSpeech.notEquals=" + DEFAULT_PART_OF_SPEECH);

        // Get all the wordList where partOfSpeech not equals to UPDATED_PART_OF_SPEECH
        defaultWordShouldBeFound("partOfSpeech.notEquals=" + UPDATED_PART_OF_SPEECH);
    }

    @Test
    @Transactional
    public void getAllWordsByPartOfSpeechIsInShouldWork() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where partOfSpeech in DEFAULT_PART_OF_SPEECH or UPDATED_PART_OF_SPEECH
        defaultWordShouldBeFound("partOfSpeech.in=" + DEFAULT_PART_OF_SPEECH + "," + UPDATED_PART_OF_SPEECH);

        // Get all the wordList where partOfSpeech equals to UPDATED_PART_OF_SPEECH
        defaultWordShouldNotBeFound("partOfSpeech.in=" + UPDATED_PART_OF_SPEECH);
    }

    @Test
    @Transactional
    public void getAllWordsByPartOfSpeechIsNullOrNotNull() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where partOfSpeech is not null
        defaultWordShouldBeFound("partOfSpeech.specified=true");

        // Get all the wordList where partOfSpeech is null
        defaultWordShouldNotBeFound("partOfSpeech.specified=false");
    }
                @Test
    @Transactional
    public void getAllWordsByPartOfSpeechContainsSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where partOfSpeech contains DEFAULT_PART_OF_SPEECH
        defaultWordShouldBeFound("partOfSpeech.contains=" + DEFAULT_PART_OF_SPEECH);

        // Get all the wordList where partOfSpeech contains UPDATED_PART_OF_SPEECH
        defaultWordShouldNotBeFound("partOfSpeech.contains=" + UPDATED_PART_OF_SPEECH);
    }

    @Test
    @Transactional
    public void getAllWordsByPartOfSpeechNotContainsSomething() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        // Get all the wordList where partOfSpeech does not contain DEFAULT_PART_OF_SPEECH
        defaultWordShouldNotBeFound("partOfSpeech.doesNotContain=" + DEFAULT_PART_OF_SPEECH);

        // Get all the wordList where partOfSpeech does not contain UPDATED_PART_OF_SPEECH
        defaultWordShouldBeFound("partOfSpeech.doesNotContain=" + UPDATED_PART_OF_SPEECH);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWordShouldBeFound(String filter) throws Exception {
        restWordMockMvc.perform(get("/api/words?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(word.getId().intValue())))
            .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD)))
            .andExpect(jsonPath("$.[*].translate").value(hasItem(DEFAULT_TRANSLATE)))
            .andExpect(jsonPath("$.[*].partOfSpeech").value(hasItem(DEFAULT_PART_OF_SPEECH)));

        // Check, that the count call also returns 1
        restWordMockMvc.perform(get("/api/words/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWordShouldNotBeFound(String filter) throws Exception {
        restWordMockMvc.perform(get("/api/words?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWordMockMvc.perform(get("/api/words/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWord() throws Exception {
        // Get the word
        restWordMockMvc.perform(get("/api/words/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        int databaseSizeBeforeUpdate = wordRepository.findAll().size();

        // Update the word
        Word updatedWord = wordRepository.findById(word.getId()).get();
        // Disconnect from session so that the updates on updatedWord are not directly saved in db
        em.detach(updatedWord);
        updatedWord
            .word(UPDATED_WORD)
            .translate(UPDATED_TRANSLATE)
            .partOfSpeech(UPDATED_PART_OF_SPEECH);
        WordDTO wordDTO = wordMapper.toDto(updatedWord);

        restWordMockMvc.perform(put("/api/words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isOk());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeUpdate);
        Word testWord = wordList.get(wordList.size() - 1);
        assertThat(testWord.getWord()).isEqualTo(UPDATED_WORD);
        assertThat(testWord.getTranslate()).isEqualTo(UPDATED_TRANSLATE);
        assertThat(testWord.getPartOfSpeech()).isEqualTo(UPDATED_PART_OF_SPEECH);
    }

    @Test
    @Transactional
    public void updateNonExistingWord() throws Exception {
        int databaseSizeBeforeUpdate = wordRepository.findAll().size();

        // Create the Word
        WordDTO wordDTO = wordMapper.toDto(word);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWordMockMvc.perform(put("/api/words")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Word in the database
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWord() throws Exception {
        // Initialize the database
        wordRepository.saveAndFlush(word);

        int databaseSizeBeforeDelete = wordRepository.findAll().size();

        // Delete the word
        restWordMockMvc.perform(delete("/api/words/{id}", word.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Word> wordList = wordRepository.findAll();
        assertThat(wordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
