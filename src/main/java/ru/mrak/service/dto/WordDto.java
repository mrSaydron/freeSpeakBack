package ru.mrak.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.mrak.model.entity.Word;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link Word} entity.
 */
@ToString
@Getter
@Setter
public class WordDto implements Serializable {

    private Long id;

    @NotNull
    private String word;

    private String translate;
    private String partOfSpeech;
    private Long totalAmount;
    private String pictureId;
    private String audioId;
    private Double frequency;
    private Boolean userHas;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WordDto)) {
            return false;
        }

        return id != null && id.equals(((WordDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
