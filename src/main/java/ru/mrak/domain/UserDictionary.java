package ru.mrak.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "user_dictionary")
public class UserDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "base_language")
    private String baseLanguage;

    @Column(name = "target_language")
    private String targetLanguage;

    @OneToMany(mappedBy = "dictionary", fetch = FetchType.LAZY)
    private Collection<UserDictionaryHasWord> dictionaryWords = new ArrayList<>();

    public UserDictionary() {
    }

    public UserDictionary(User user, String baseLanguage, String targetLanguage) {
        this.user = user;
        this.baseLanguage = baseLanguage;
        this.targetLanguage = targetLanguage;
    }

    public UserDictionary(Long id, User user, String baseLanguage, String targetLanguage, Collection<UserDictionaryHasWord> dictionaryWords) {
        this.id = id;
        this.user = user;
        this.baseLanguage = baseLanguage;
        this.targetLanguage = targetLanguage;
        this.dictionaryWords = dictionaryWords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBaseLanguage() {
        return baseLanguage;
    }

    public void setBaseLanguage(String baseLanguage) {
        this.baseLanguage = baseLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public Collection<UserDictionaryHasWord> getDictionaryWords() {
        return dictionaryWords;
    }

    public void setDictionaryWords(Collection<UserDictionaryHasWord> dictionaryWords) {
        this.dictionaryWords = dictionaryWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDictionary that = (UserDictionary) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserDictionary{" +
            "id=" + id +
            ", userId=" + user.getId() +
            ", baseLanguage='" + baseLanguage + '\'' +
            ", targetLanguage='" + targetLanguage + '\'' +
            ", dictionaryWords=" + dictionaryWords +
            '}';
    }
}
