package ru.mrak.model.entity.userWordProgress;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_word")
public class UserWord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_word_seq")
    @SequenceGenerator(name = "user_word_seq", sequenceName = "user_word_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    @ElementCollection
    @CollectionTable(
        name = "user_word_has_progress",
        joinColumns = @JoinColumn(name = "user_word_id")
    )
    @CollectionId(
        columns = @Column(name = "id"),
        type = @Type(type = "long"),
        generator = "user_word_has_progress_seq"
    )
    @SequenceGenerator(name = "user_word_has_progress_seq", sequenceName = "user_word_has_progress_seq", allocationSize = 1)
    private List<UserWordHasProgress> wordProgresses = new ArrayList<>();

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

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public List<UserWordHasProgress> getWordProgresses() {
        return wordProgresses;
    }

    public void setWordProgresses(List<UserWordHasProgress> wordProgresses) {
        this.wordProgresses = wordProgresses;
    }

    @Override
    public String toString() {
        return "UserWord{" +
            "id=" + id +
            '}';
    }
}
