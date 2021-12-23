package ru.mrak.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "text_tag")
public class TextTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "text_tag_seq")
    @SequenceGenerator(name = "text_tag_seq", sequenceName = "text_tag_seq", allocationSize = 1)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "type_name")
    private String typeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "text_tag_parent_id")
    private TextTag parent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextTag textTag = (TextTag) o;
        return id.equals(textTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
