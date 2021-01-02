package ru.mrak.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.mrak.web.rest.TestUtil;

public class DictionaryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dictionary.class);
        Dictionary dictionary1 = new Dictionary();
        dictionary1.setId(1L);
        Dictionary dictionary2 = new Dictionary();
        dictionary2.setId(dictionary1.getId());
        assertThat(dictionary1).isEqualTo(dictionary2);
        dictionary2.setId(2L);
        assertThat(dictionary1).isNotEqualTo(dictionary2);
        dictionary1.setId(null);
        assertThat(dictionary1).isNotEqualTo(dictionary2);
    }
}
