package ru.mrak.web.rest;

import ru.mrak.LibFourApp;
import ru.mrak.domain.Message;
import ru.mrak.domain.User;
import ru.mrak.domain.Chat;
import ru.mrak.repository.MessageRepository;
import ru.mrak.service.MessageService;
import ru.mrak.service.dto.MessageDTO;
import ru.mrak.service.mapper.MessageMapper;
import ru.mrak.service.dto.MessageCriteria;
import ru.mrak.service.MessageQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ru.mrak.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MessageResource} REST controller.
 */
@SpringBootTest(classes = LibFourApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MessageResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageQueryService messageQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageMockMvc;

    private Message message;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Message createEntity(EntityManager em) {
        Message message = new Message()
            .message(DEFAULT_MESSAGE)
            .time(DEFAULT_TIME);
        return message;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Message createUpdatedEntity(EntityManager em) {
        Message message = new Message()
            .message(UPDATED_MESSAGE)
            .time(UPDATED_TIME);
        return message;
    }

    @BeforeEach
    public void initTest() {
        message = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessage() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate + 1);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testMessage.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message with an existing ID
        message.setId(1L);
        MessageDTO messageDTO = messageMapper.toDto(message);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setTime(null);

        // Create the Message, which fails.
        MessageDTO messageDTO = messageMapper.toDto(message);


        restMessageMockMvc.perform(post("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessages() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }
    
    @Test
    @Transactional
    public void getMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(message.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }


    @Test
    @Transactional
    public void getMessagesByIdFiltering() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        Long id = message.getId();

        defaultMessageShouldBeFound("id.equals=" + id);
        defaultMessageShouldNotBeFound("id.notEquals=" + id);

        defaultMessageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMessageShouldNotBeFound("id.greaterThan=" + id);

        defaultMessageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMessageShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMessagesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message equals to DEFAULT_MESSAGE
        defaultMessageShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the messageList where message equals to UPDATED_MESSAGE
        defaultMessageShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message not equals to DEFAULT_MESSAGE
        defaultMessageShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the messageList where message not equals to UPDATED_MESSAGE
        defaultMessageShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultMessageShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the messageList where message equals to UPDATED_MESSAGE
        defaultMessageShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message is not null
        defaultMessageShouldBeFound("message.specified=true");

        // Get all the messageList where message is null
        defaultMessageShouldNotBeFound("message.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessagesByMessageContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message contains DEFAULT_MESSAGE
        defaultMessageShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the messageList where message contains UPDATED_MESSAGE
        defaultMessageShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message does not contain DEFAULT_MESSAGE
        defaultMessageShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the messageList where message does not contain UPDATED_MESSAGE
        defaultMessageShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllMessagesByTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where time equals to DEFAULT_TIME
        defaultMessageShouldBeFound("time.equals=" + DEFAULT_TIME);

        // Get all the messageList where time equals to UPDATED_TIME
        defaultMessageShouldNotBeFound("time.equals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllMessagesByTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where time not equals to DEFAULT_TIME
        defaultMessageShouldNotBeFound("time.notEquals=" + DEFAULT_TIME);

        // Get all the messageList where time not equals to UPDATED_TIME
        defaultMessageShouldBeFound("time.notEquals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllMessagesByTimeIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where time in DEFAULT_TIME or UPDATED_TIME
        defaultMessageShouldBeFound("time.in=" + DEFAULT_TIME + "," + UPDATED_TIME);

        // Get all the messageList where time equals to UPDATED_TIME
        defaultMessageShouldNotBeFound("time.in=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllMessagesByTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where time is not null
        defaultMessageShouldBeFound("time.specified=true");

        // Get all the messageList where time is null
        defaultMessageShouldNotBeFound("time.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessagesByTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where time is greater than or equal to DEFAULT_TIME
        defaultMessageShouldBeFound("time.greaterThanOrEqual=" + DEFAULT_TIME);

        // Get all the messageList where time is greater than or equal to UPDATED_TIME
        defaultMessageShouldNotBeFound("time.greaterThanOrEqual=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllMessagesByTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where time is less than or equal to DEFAULT_TIME
        defaultMessageShouldBeFound("time.lessThanOrEqual=" + DEFAULT_TIME);

        // Get all the messageList where time is less than or equal to SMALLER_TIME
        defaultMessageShouldNotBeFound("time.lessThanOrEqual=" + SMALLER_TIME);
    }

    @Test
    @Transactional
    public void getAllMessagesByTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where time is less than DEFAULT_TIME
        defaultMessageShouldNotBeFound("time.lessThan=" + DEFAULT_TIME);

        // Get all the messageList where time is less than UPDATED_TIME
        defaultMessageShouldBeFound("time.lessThan=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllMessagesByTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where time is greater than DEFAULT_TIME
        defaultMessageShouldNotBeFound("time.greaterThan=" + DEFAULT_TIME);

        // Get all the messageList where time is greater than SMALLER_TIME
        defaultMessageShouldBeFound("time.greaterThan=" + SMALLER_TIME);
    }


    @Test
    @Transactional
    public void getAllMessagesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        message.setUser(user);
        messageRepository.saveAndFlush(message);
        Long userId = user.getId();

        // Get all the messageList where user equals to userId
        defaultMessageShouldBeFound("userId.equals=" + userId);

        // Get all the messageList where user equals to userId + 1
        defaultMessageShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllMessagesByChatIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);
        Chat chat = ChatResourceIT.createEntity(em);
        em.persist(chat);
        em.flush();
        message.setChat(chat);
        messageRepository.saveAndFlush(message);
        Long chatId = chat.getId();

        // Get all the messageList where chat equals to chatId
        defaultMessageShouldBeFound("chatId.equals=" + chatId);

        // Get all the messageList where chat equals to chatId + 1
        defaultMessageShouldNotBeFound("chatId.equals=" + (chatId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMessageShouldBeFound(String filter) throws Exception {
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));

        // Check, that the count call also returns 1
        restMessageMockMvc.perform(get("/api/messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMessageShouldNotBeFound(String filter) throws Exception {
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMessageMockMvc.perform(get("/api/messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message
        Message updatedMessage = messageRepository.findById(message.getId()).get();
        // Disconnect from session so that the updates on updatedMessage are not directly saved in db
        em.detach(updatedMessage);
        updatedMessage
            .message(UPDATED_MESSAGE)
            .time(UPDATED_TIME);
        MessageDTO messageDTO = messageMapper.toDto(updatedMessage);

        restMessageMockMvc.perform(put("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testMessage.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageMockMvc.perform(put("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        int databaseSizeBeforeDelete = messageRepository.findAll().size();

        // Delete the message
        restMessageMockMvc.perform(delete("/api/messages/{id}", message.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
