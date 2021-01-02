package ru.mrak.web.rest;

import ru.mrak.LibFourApp;
import ru.mrak.domain.Book;
import ru.mrak.domain.Dictionary;
import ru.mrak.domain.User;
import ru.mrak.repository.BookRepository;
import ru.mrak.service.BookService;
import ru.mrak.service.dto.BookDTO;
import ru.mrak.service.mapper.BookMapper;
import ru.mrak.service.dto.BookCriteria;
import ru.mrak.service.BookQueryService;

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
 * Integration tests for the {@link BookResource} REST controller.
 */
@SpringBootTest(classes = LibFourApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BookResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PUBLIC_BOOK = false;
    private static final Boolean UPDATED_PUBLIC_BOOK = true;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookQueryService bookQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookMockMvc;

    private Book book;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createEntity(EntityManager em) {
        Book book = new Book()
            .title(DEFAULT_TITLE)
            .author(DEFAULT_AUTHOR)
            .source(DEFAULT_SOURCE)
            .text(DEFAULT_TEXT)
            .publicBook(DEFAULT_PUBLIC_BOOK);
        return book;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Book createUpdatedEntity(EntityManager em) {
        Book book = new Book()
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .source(UPDATED_SOURCE)
            .text(UPDATED_TEXT)
            .publicBook(UPDATED_PUBLIC_BOOK);
        return book;
    }

    @BeforeEach
    public void initTest() {
        book = createEntity(em);
    }

    @Test
    @Transactional
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();
        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);
        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBook.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBook.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testBook.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testBook.isPublicBook()).isEqualTo(DEFAULT_PUBLIC_BOOK);
    }

    @Test
    @Transactional
    public void createBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book with an existing ID
        book.setId(1L);
        BookDTO bookDTO = bookMapper.toDto(book);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setTitle(null);

        // Create the Book, which fails.
        BookDTO bookDTO = bookMapper.toDto(book);


        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setText(null);

        // Create the Book, which fails.
        BookDTO bookDTO = bookMapper.toDto(book);


        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublicBookIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookRepository.findAll().size();
        // set the field null
        book.setPublicBook(null);

        // Create the Book, which fails.
        BookDTO bookDTO = bookMapper.toDto(book);


        restBookMockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].publicBook").value(hasItem(DEFAULT_PUBLIC_BOOK.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(book.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.publicBook").value(DEFAULT_PUBLIC_BOOK.booleanValue()));
    }


    @Test
    @Transactional
    public void getBooksByIdFiltering() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        Long id = book.getId();

        defaultBookShouldBeFound("id.equals=" + id);
        defaultBookShouldNotBeFound("id.notEquals=" + id);

        defaultBookShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.greaterThan=" + id);

        defaultBookShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBooksByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title equals to DEFAULT_TITLE
        defaultBookShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title not equals to DEFAULT_TITLE
        defaultBookShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the bookList where title not equals to UPDATED_TITLE
        defaultBookShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultBookShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the bookList where title equals to UPDATED_TITLE
        defaultBookShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title is not null
        defaultBookShouldBeFound("title.specified=true");

        // Get all the bookList where title is null
        defaultBookShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByTitleContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title contains DEFAULT_TITLE
        defaultBookShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the bookList where title contains UPDATED_TITLE
        defaultBookShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllBooksByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where title does not contain DEFAULT_TITLE
        defaultBookShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the bookList where title does not contain UPDATED_TITLE
        defaultBookShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllBooksByAuthorIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author equals to DEFAULT_AUTHOR
        defaultBookShouldBeFound("author.equals=" + DEFAULT_AUTHOR);

        // Get all the bookList where author equals to UPDATED_AUTHOR
        defaultBookShouldNotBeFound("author.equals=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void getAllBooksByAuthorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author not equals to DEFAULT_AUTHOR
        defaultBookShouldNotBeFound("author.notEquals=" + DEFAULT_AUTHOR);

        // Get all the bookList where author not equals to UPDATED_AUTHOR
        defaultBookShouldBeFound("author.notEquals=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void getAllBooksByAuthorIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author in DEFAULT_AUTHOR or UPDATED_AUTHOR
        defaultBookShouldBeFound("author.in=" + DEFAULT_AUTHOR + "," + UPDATED_AUTHOR);

        // Get all the bookList where author equals to UPDATED_AUTHOR
        defaultBookShouldNotBeFound("author.in=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void getAllBooksByAuthorIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author is not null
        defaultBookShouldBeFound("author.specified=true");

        // Get all the bookList where author is null
        defaultBookShouldNotBeFound("author.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByAuthorContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author contains DEFAULT_AUTHOR
        defaultBookShouldBeFound("author.contains=" + DEFAULT_AUTHOR);

        // Get all the bookList where author contains UPDATED_AUTHOR
        defaultBookShouldNotBeFound("author.contains=" + UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void getAllBooksByAuthorNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where author does not contain DEFAULT_AUTHOR
        defaultBookShouldNotBeFound("author.doesNotContain=" + DEFAULT_AUTHOR);

        // Get all the bookList where author does not contain UPDATED_AUTHOR
        defaultBookShouldBeFound("author.doesNotContain=" + UPDATED_AUTHOR);
    }


    @Test
    @Transactional
    public void getAllBooksBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where source equals to DEFAULT_SOURCE
        defaultBookShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the bookList where source equals to UPDATED_SOURCE
        defaultBookShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllBooksBySourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where source not equals to DEFAULT_SOURCE
        defaultBookShouldNotBeFound("source.notEquals=" + DEFAULT_SOURCE);

        // Get all the bookList where source not equals to UPDATED_SOURCE
        defaultBookShouldBeFound("source.notEquals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllBooksBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultBookShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the bookList where source equals to UPDATED_SOURCE
        defaultBookShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllBooksBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where source is not null
        defaultBookShouldBeFound("source.specified=true");

        // Get all the bookList where source is null
        defaultBookShouldNotBeFound("source.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksBySourceContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where source contains DEFAULT_SOURCE
        defaultBookShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the bookList where source contains UPDATED_SOURCE
        defaultBookShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllBooksBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where source does not contain DEFAULT_SOURCE
        defaultBookShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the bookList where source does not contain UPDATED_SOURCE
        defaultBookShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }


    @Test
    @Transactional
    public void getAllBooksByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where text equals to DEFAULT_TEXT
        defaultBookShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the bookList where text equals to UPDATED_TEXT
        defaultBookShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllBooksByTextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where text not equals to DEFAULT_TEXT
        defaultBookShouldNotBeFound("text.notEquals=" + DEFAULT_TEXT);

        // Get all the bookList where text not equals to UPDATED_TEXT
        defaultBookShouldBeFound("text.notEquals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllBooksByTextIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultBookShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the bookList where text equals to UPDATED_TEXT
        defaultBookShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllBooksByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where text is not null
        defaultBookShouldBeFound("text.specified=true");

        // Get all the bookList where text is null
        defaultBookShouldNotBeFound("text.specified=false");
    }
                @Test
    @Transactional
    public void getAllBooksByTextContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where text contains DEFAULT_TEXT
        defaultBookShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the bookList where text contains UPDATED_TEXT
        defaultBookShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllBooksByTextNotContainsSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where text does not contain DEFAULT_TEXT
        defaultBookShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the bookList where text does not contain UPDATED_TEXT
        defaultBookShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }


    @Test
    @Transactional
    public void getAllBooksByPublicBookIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicBook equals to DEFAULT_PUBLIC_BOOK
        defaultBookShouldBeFound("publicBook.equals=" + DEFAULT_PUBLIC_BOOK);

        // Get all the bookList where publicBook equals to UPDATED_PUBLIC_BOOK
        defaultBookShouldNotBeFound("publicBook.equals=" + UPDATED_PUBLIC_BOOK);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicBookIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicBook not equals to DEFAULT_PUBLIC_BOOK
        defaultBookShouldNotBeFound("publicBook.notEquals=" + DEFAULT_PUBLIC_BOOK);

        // Get all the bookList where publicBook not equals to UPDATED_PUBLIC_BOOK
        defaultBookShouldBeFound("publicBook.notEquals=" + UPDATED_PUBLIC_BOOK);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicBookIsInShouldWork() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicBook in DEFAULT_PUBLIC_BOOK or UPDATED_PUBLIC_BOOK
        defaultBookShouldBeFound("publicBook.in=" + DEFAULT_PUBLIC_BOOK + "," + UPDATED_PUBLIC_BOOK);

        // Get all the bookList where publicBook equals to UPDATED_PUBLIC_BOOK
        defaultBookShouldNotBeFound("publicBook.in=" + UPDATED_PUBLIC_BOOK);
    }

    @Test
    @Transactional
    public void getAllBooksByPublicBookIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        // Get all the bookList where publicBook is not null
        defaultBookShouldBeFound("publicBook.specified=true");

        // Get all the bookList where publicBook is null
        defaultBookShouldNotBeFound("publicBook.specified=false");
    }

    @Test
    @Transactional
    public void getAllBooksByDictionaryIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        Dictionary dictionary = DictionaryResourceIT.createEntity(em);
        em.persist(dictionary);
        em.flush();
        book.setDictionary(dictionary);
        bookRepository.saveAndFlush(book);
        Long dictionaryId = dictionary.getId();

        // Get all the bookList where dictionary equals to dictionaryId
        defaultBookShouldBeFound("dictionaryId.equals=" + dictionaryId);

        // Get all the bookList where dictionary equals to dictionaryId + 1
        defaultBookShouldNotBeFound("dictionaryId.equals=" + (dictionaryId + 1));
    }


    @Test
    @Transactional
    public void getAllBooksByLoadedUserIsEqualToSomething() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);
        User loadedUser = UserResourceIT.createEntity(em);
        em.persist(loadedUser);
        em.flush();
        book.setLoadedUser(loadedUser);
        bookRepository.saveAndFlush(book);
        Long loadedUserId = loadedUser.getId();

        // Get all the bookList where loadedUser equals to loadedUserId
        defaultBookShouldBeFound("loadedUserId.equals=" + loadedUserId);

        // Get all the bookList where loadedUser equals to loadedUserId + 1
        defaultBookShouldNotBeFound("loadedUserId.equals=" + (loadedUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookShouldBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].publicBook").value(hasItem(DEFAULT_PUBLIC_BOOK.booleanValue())));

        // Check, that the count call also returns 1
        restBookMockMvc.perform(get("/api/books/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookShouldNotBeFound(String filter) throws Exception {
        restBookMockMvc.perform(get("/api/books?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookMockMvc.perform(get("/api/books/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = bookRepository.findById(book.getId()).get();
        // Disconnect from session so that the updates on updatedBook are not directly saved in db
        em.detach(updatedBook);
        updatedBook
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .source(UPDATED_SOURCE)
            .text(UPDATED_TEXT)
            .publicBook(UPDATED_PUBLIC_BOOK);
        BookDTO bookDTO = bookMapper.toDto(updatedBook);

        restBookMockMvc.perform(put("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
        Book testBook = bookList.get(bookList.size() - 1);
        assertThat(testBook.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBook.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBook.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testBook.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testBook.isPublicBook()).isEqualTo(UPDATED_PUBLIC_BOOK);
    }

    @Test
    @Transactional
    public void updateNonExistingBook() throws Exception {
        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Create the Book
        BookDTO bookDTO = bookMapper.toDto(book);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookMockMvc.perform(put("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Book in the database
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBook() throws Exception {
        // Initialize the database
        bookRepository.saveAndFlush(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Delete the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Book> bookList = bookRepository.findAll();
        assertThat(bookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
