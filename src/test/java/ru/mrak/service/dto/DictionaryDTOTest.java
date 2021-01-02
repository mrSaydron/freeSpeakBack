package ru.mrak.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.mrak.web.rest.TestUtil;

public class DictionaryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DictionaryDTO.class);
        DictionaryDTO dictionaryDTO1 = new DictionaryDTO();
        dictionaryDTO1.setId(1L);
        DictionaryDTO dictionaryDTO2 = new DictionaryDTO();
        assertThat(dictionaryDTO1).isNotEqualTo(dictionaryDTO2);
        dictionaryDTO2.setId(dictionaryDTO1.getId());
        assertThat(dictionaryDTO1).isEqualTo(dictionaryDTO2);
        dictionaryDTO2.setId(2L);
        assertThat(dictionaryDTO1).isNotEqualTo(dictionaryDTO2);
        dictionaryDTO1.setId(null);
        assertThat(dictionaryDTO1).isNotEqualTo(dictionaryDTO2);
    }
}
