package ru.mrak.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import ru.mrak.domain.enumeration.chatTypeEnum;

/**
 * A Chat.
 */
@Entity
@Table(name = "chat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "chat_type", nullable = false)
    private chatTypeEnum chatType;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "chat_user",
               joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public chatTypeEnum getChatType() {
        return chatType;
    }

    public Chat chatType(chatTypeEnum chatType) {
        this.chatType = chatType;
        return this;
    }

    public void setChatType(chatTypeEnum chatType) {
        this.chatType = chatType;
    }

    public String getTitle() {
        return title;
    }

    public Chat title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Chat users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Chat addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Chat removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chat)) {
            return false;
        }
        return id != null && id.equals(((Chat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Chat{" +
            "id=" + getId() +
            ", chatType='" + getChatType() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
