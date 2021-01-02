package ru.mrak.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.mrak.web.rest.TestUtil;

public class DictionaryHasWordTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DictionaryHasWord.class);
        DictionaryHasWord dictionaryHasWord1 = new DictionaryHasWord();
        dictionaryHasWord1.setId(1L);
        DictionaryHasWord dictionaryHasWord2 = new DictionaryHasWord();
        dictionaryHasWord2.setId(dictionaryHasWord1.getId());
        assertThat(dictionaryHasWord1).isEqualTo(dictionaryHasWord2);
        dictionaryHasWord2.setId(2L);
        assertThat(dictionaryHasWord1).isNotEqualTo(dictionaryHasWord2);
        dictionaryHasWord1.setId(null);
        assertThat(dictionaryHasWord1).isNotEqualTo(dictionaryHasWord2);
    }
}
