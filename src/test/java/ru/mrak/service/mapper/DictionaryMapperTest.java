package ru.mrak.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DictionaryMapperTest {

    private DictionaryMapper dictionaryMapper;

    @BeforeEach
    public void setUp() {
        dictionaryMapper = new DictionaryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dictionaryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dictionaryMapper.fromId(null)).isNull();
    }
}
