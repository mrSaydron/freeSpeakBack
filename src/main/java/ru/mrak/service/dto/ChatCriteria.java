package ru.mrak.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import ru.mrak.domain.enumeration.ChatTypeEnum;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ru.mrak.domain.Chat} entity. This class is used
 * in {@link ru.mrak.web.rest.ChatResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /chats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChatCriteria implements Serializable, Criteria {
    /**
     * Class for filtering chatTypeEnum
     */
    public static class chatTypeEnumFilter extends Filter<ChatTypeEnum> {

        public chatTypeEnumFilter() {
        }

        public chatTypeEnumFilter(chatTypeEnumFilter filter) {
            super(filter);
        }

        @Override
        public chatTypeEnumFilter copy() {
            return new chatTypeEnumFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private chatTypeEnumFilter chatType;

    private StringFilter title;

    private LongFilter userId;

    public ChatCriteria() {
    }

    public ChatCriteria(ChatCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.chatType = other.chatType == null ? null : other.chatType.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public ChatCriteria copy() {
        return new ChatCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public chatTypeEnumFilter getChatType() {
        return chatType;
    }

    public void setChatType(chatTypeEnumFilter chatType) {
        this.chatType = chatType;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChatCriteria that = (ChatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(chatType, that.chatType) &&
            Objects.equals(title, that.title) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        chatType,
        title,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (chatType != null ? "chatType=" + chatType + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
