package ru.mrak.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.mrak.web.rest.TestUtil;

public class DictionaryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookDictionaryDTO.class);
        BookDictionaryDTO dictionaryDTO1 = new BookDictionaryDTO();
        dictionaryDTO1.setId(1L);
        BookDictionaryDTO dictionaryDTO2 = new BookDictionaryDTO();
        assertThat(dictionaryDTO1).isNotEqualTo(dictionaryDTO2);
        dictionaryDTO2.setId(dictionaryDTO1.getId());
        assertThat(dictionaryDTO1).isEqualTo(dictionaryDTO2);
        dictionaryDTO2.setId(2L);
        assertThat(dictionaryDTO1).isNotEqualTo(dictionaryDTO2);
        dictionaryDTO1.setId(null);
        assertThat(dictionaryDTO1).isNotEqualTo(dictionaryDTO2);
    }
}
