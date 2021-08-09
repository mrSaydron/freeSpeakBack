package ru.mrak.domain.abby;

import lombok.Getter;

@Getter
public enum NodeType {
    Comment,
    Paragraph,
    Text,
    List,
    ListItem,
    Examples,
    ExampleItem,
    Example,
    CardRefs,
    CardRefItem,
    CardRef,
    Transcription,
    Abbrev,
    Caption,
    Sound,
    Ref,
    Unsupported,
}
