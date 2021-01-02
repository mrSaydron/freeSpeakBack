package ru.mrak.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.mrak.web.rest.TestUtil;

public class DictionaryHasWordDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DictionaryHasWordDTO.class);
        DictionaryHasWordDTO dictionaryHasWordDTO1 = new DictionaryHasWordDTO();
        dictionaryHasWordDTO1.setId(1L);
        DictionaryHasWordDTO dictionaryHasWordDTO2 = new DictionaryHasWordDTO();
        assertThat(dictionaryHasWordDTO1).isNotEqualTo(dictionaryHasWordDTO2);
        dictionaryHasWordDTO2.setId(dictionaryHasWordDTO1.getId());
        assertThat(dictionaryHasWordDTO1).isEqualTo(dictionaryHasWordDTO2);
        dictionaryHasWordDTO2.setId(2L);
        assertThat(dictionaryHasWordDTO1).isNotEqualTo(dictionaryHasWordDTO2);
        dictionaryHasWordDTO1.setId(null);
        assertThat(dictionaryHasWordDTO1).isNotEqualTo(dictionaryHasWordDTO2);
    }
}
