package ru.mrak.domain.abby;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleModel {

    @JsonProperty("Title")
    private String title;

    @JsonProperty("TitleMarkup")
    private List<ArticleNode> titleMarkup;

    @JsonProperty("Dictionary")
    private String dictionary;

    @JsonProperty("ArticleId")
    private String articleId;

    @JsonProperty("Body")
    private List<ArticleNode> body;
}
