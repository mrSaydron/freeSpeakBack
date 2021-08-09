package ru.mrak.domain.abby;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleNode {

    @JsonProperty("Type")
    private Integer type;

    @JsonProperty("Items")
    private List<ArticleNode> items;

    @JsonProperty("Markup")
    private ArticleNode markup;

    @JsonProperty("Node")
    private NodeType node;

    @JsonProperty("Text")
    private String text;

    @JsonProperty("IsOptional")
    private Boolean isOptional;

    @JsonProperty("IsItalic")
    private Boolean isItalic;

    @JsonProperty("IsAccent")
    private Boolean isAccent;

    @JsonProperty("FullText")
    private String fullText;

    @JsonProperty("FileName")
    private String fileName;
}
