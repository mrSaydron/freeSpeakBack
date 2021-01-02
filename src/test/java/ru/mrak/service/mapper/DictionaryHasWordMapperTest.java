package ru.mrak.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DictionaryHasWordMapperTest {

    private DictionaryHasWordMapper dictionaryHasWordMapper;

    @BeforeEach
    public void setUp() {
        dictionaryHasWordMapper = new DictionaryHasWordMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dictionaryHasWordMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dictionaryHasWordMapper.fromId(null)).isNull();
    }
}
