package ru.mrak.model.entity.userWordProgress;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;
import ru.mrak.model.entity.User;
import ru.mrak.model.entity.Word;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_word")
@Getter
@Setter
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

    @Column(name = "priority")
    private long priority = Integer.MAX_VALUE;

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

    @Override
    public String toString() {
        return "UserWord{" +
            "id=" + id +
            '}';
    }
}
