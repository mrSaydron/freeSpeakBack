package ru.mrak.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import ru.mrak.model.enumeration.ChatTypeEnum;

/**
 * A DTO for the {@link ru.mrak.model.Chat} entity.
 */
public class ChatDTO implements Serializable {

    private Long id;

    @NotNull
    private ChatTypeEnum chatType;

    private String title;

    private Set<UserDTO> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatTypeEnum getChatType() {
        return chatType;
    }

    public void setChatType(ChatTypeEnum chatType) {
        this.chatType = chatType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChatDTO)) {
            return false;
        }

        return id != null && id.equals(((ChatDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChatDTO{" +
            "id=" + getId() +
            ", chatType='" + getChatType() + "'" +
            ", title='" + getTitle() + "'" +
            ", users='" + getUsers() + "'" +
            "}";
    }
}
