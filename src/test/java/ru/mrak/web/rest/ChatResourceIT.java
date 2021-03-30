package ru.mrak.web.rest;

import ru.mrak.LibFourApp;
import ru.mrak.domain.Chat;
import ru.mrak.domain.User;
import ru.mrak.domain.enumeration.ChatTypeEnum;
import ru.mrak.repository.ChatRepository;
import ru.mrak.service.ChatService;
import ru.mrak.service.dto.ChatDTO;
import ru.mrak.service.mapper.ChatMapper;
import ru.mrak.service.ChatQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ChatResource} REST controller.
 */
@SpringBootTest(classes = LibFourApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ChatResourceIT {

    private static final ChatTypeEnum DEFAULT_CHAT_TYPE = ChatTypeEnum.SUPPORT;
    private static final ChatTypeEnum UPDATED_CHAT_TYPE = ChatTypeEnum.COMMON;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private ChatRepository chatRepository;

    @Mock
    private ChatRepository chatRepositoryMock;

    @Autowired
    private ChatMapper chatMapper;

    @Mock
    private ChatService chatServiceMock;

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatQueryService chatQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChatMockMvc;

    private Chat chat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chat createEntity(EntityManager em) {
        Chat chat = new Chat()
            .chatType(DEFAULT_CHAT_TYPE)
            .title(DEFAULT_TITLE);
        return chat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chat createUpdatedEntity(EntityManager em) {
        Chat chat = new Chat()
            .chatType(UPDATED_CHAT_TYPE)
            .title(UPDATED_TITLE);
        return chat;
    }

    @BeforeEach
    public void initTest() {
        chat = createEntity(em);
    }

    @Test
    @Transactional
    public void createChat() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();
        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);
        restChatMockMvc.perform(post("/api/chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isCreated());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate + 1);
        Chat testChat = chatList.get(chatList.size() - 1);
        assertThat(testChat.getChatType()).isEqualTo(DEFAULT_CHAT_TYPE);
        assertThat(testChat.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createChatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chatRepository.findAll().size();

        // Create the Chat with an existing ID
        chat.setId(1L);
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChatMockMvc.perform(post("/api/chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkChatTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = chatRepository.findAll().size();
        // set the field null
        chat.setChatType(null);

        // Create the Chat, which fails.
        ChatDTO chatDTO = chatMapper.toDto(chat);


        restChatMockMvc.perform(post("/api/chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isBadRequest());

        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChats() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList
        restChatMockMvc.perform(get("/api/chats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chat.getId().intValue())))
            .andExpect(jsonPath("$.[*].chatType").value(hasItem(DEFAULT_CHAT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }

    @SuppressWarnings({"unchecked"})
    public void getAllChatsWithEagerRelationshipsIsEnabled() throws Exception {
        when(chatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChatMockMvc.perform(get("/api/chats?eagerload=true"))
            .andExpect(status().isOk());

        verify(chatServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllChatsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(chatServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restChatMockMvc.perform(get("/api/chats?eagerload=true"))
            .andExpect(status().isOk());

        verify(chatServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get the chat
        restChatMockMvc.perform(get("/api/chats/{id}", chat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chat.getId().intValue()))
            .andExpect(jsonPath("$.chatType").value(DEFAULT_CHAT_TYPE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }


    @Test
    @Transactional
    public void getChatsByIdFiltering() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        Long id = chat.getId();

        defaultChatShouldBeFound("id.equals=" + id);
        defaultChatShouldNotBeFound("id.notEquals=" + id);

        defaultChatShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultChatShouldNotBeFound("id.greaterThan=" + id);

        defaultChatShouldBeFound("id.lessThanOrEqual=" + id);
        defaultChatShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllChatsByChatTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where chatType equals to DEFAULT_CHAT_TYPE
        defaultChatShouldBeFound("chatType.equals=" + DEFAULT_CHAT_TYPE);

        // Get all the chatList where chatType equals to UPDATED_CHAT_TYPE
        defaultChatShouldNotBeFound("chatType.equals=" + UPDATED_CHAT_TYPE);
    }

    @Test
    @Transactional
    public void getAllChatsByChatTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where chatType not equals to DEFAULT_CHAT_TYPE
        defaultChatShouldNotBeFound("chatType.notEquals=" + DEFAULT_CHAT_TYPE);

        // Get all the chatList where chatType not equals to UPDATED_CHAT_TYPE
        defaultChatShouldBeFound("chatType.notEquals=" + UPDATED_CHAT_TYPE);
    }

    @Test
    @Transactional
    public void getAllChatsByChatTypeIsInShouldWork() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where chatType in DEFAULT_CHAT_TYPE or UPDATED_CHAT_TYPE
        defaultChatShouldBeFound("chatType.in=" + DEFAULT_CHAT_TYPE + "," + UPDATED_CHAT_TYPE);

        // Get all the chatList where chatType equals to UPDATED_CHAT_TYPE
        defaultChatShouldNotBeFound("chatType.in=" + UPDATED_CHAT_TYPE);
    }

    @Test
    @Transactional
    public void getAllChatsByChatTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where chatType is not null
        defaultChatShouldBeFound("chatType.specified=true");

        // Get all the chatList where chatType is null
        defaultChatShouldNotBeFound("chatType.specified=false");
    }

    @Test
    @Transactional
    public void getAllChatsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where title equals to DEFAULT_TITLE
        defaultChatShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the chatList where title equals to UPDATED_TITLE
        defaultChatShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllChatsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where title not equals to DEFAULT_TITLE
        defaultChatShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the chatList where title not equals to UPDATED_TITLE
        defaultChatShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllChatsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultChatShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the chatList where title equals to UPDATED_TITLE
        defaultChatShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllChatsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where title is not null
        defaultChatShouldBeFound("title.specified=true");

        // Get all the chatList where title is null
        defaultChatShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllChatsByTitleContainsSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where title contains DEFAULT_TITLE
        defaultChatShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the chatList where title contains UPDATED_TITLE
        defaultChatShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllChatsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        // Get all the chatList where title does not contain DEFAULT_TITLE
        defaultChatShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the chatList where title does not contain UPDATED_TITLE
        defaultChatShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllChatsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        chat.addUser(user);
        chatRepository.saveAndFlush(chat);
        Long userId = user.getId();

        // Get all the chatList where user equals to userId
        defaultChatShouldBeFound("userId.equals=" + userId);

        // Get all the chatList where user equals to userId + 1
        defaultChatShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChatShouldBeFound(String filter) throws Exception {
        restChatMockMvc.perform(get("/api/chats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chat.getId().intValue())))
            .andExpect(jsonPath("$.[*].chatType").value(hasItem(DEFAULT_CHAT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));

        // Check, that the count call also returns 1
        restChatMockMvc.perform(get("/api/chats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChatShouldNotBeFound(String filter) throws Exception {
        restChatMockMvc.perform(get("/api/chats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChatMockMvc.perform(get("/api/chats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingChat() throws Exception {
        // Get the chat
        restChatMockMvc.perform(get("/api/chats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Update the chat
        Chat updatedChat = chatRepository.findById(chat.getId()).get();
        // Disconnect from session so that the updates on updatedChat are not directly saved in db
        em.detach(updatedChat);
        updatedChat
            .chatType(UPDATED_CHAT_TYPE)
            .title(UPDATED_TITLE);
        ChatDTO chatDTO = chatMapper.toDto(updatedChat);

        restChatMockMvc.perform(put("/api/chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isOk());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
        Chat testChat = chatList.get(chatList.size() - 1);
        assertThat(testChat.getChatType()).isEqualTo(UPDATED_CHAT_TYPE);
        assertThat(testChat.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingChat() throws Exception {
        int databaseSizeBeforeUpdate = chatRepository.findAll().size();

        // Create the Chat
        ChatDTO chatDTO = chatMapper.toDto(chat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChatMockMvc.perform(put("/api/chats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Chat in the database
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChat() throws Exception {
        // Initialize the database
        chatRepository.saveAndFlush(chat);

        int databaseSizeBeforeDelete = chatRepository.findAll().size();

        // Delete the chat
        restChatMockMvc.perform(delete("/api/chats/{id}", chat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chat> chatList = chatRepository.findAll();
        assertThat(chatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
