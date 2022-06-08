package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecursosHumanosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecursosHumanos.class);
        RecursosHumanos recursosHumanos1 = new RecursosHumanos();
        recursosHumanos1.setId("id1");
        RecursosHumanos recursosHumanos2 = new RecursosHumanos();
        recursosHumanos2.setId(recursosHumanos1.getId());
        assertThat(recursosHumanos1).isEqualTo(recursosHumanos2);
        recursosHumanos2.setId("id2");
        assertThat(recursosHumanos1).isNotEqualTo(recursosHumanos2);
        recursosHumanos1.setId(null);
        assertThat(recursosHumanos1).isNotEqualTo(recursosHumanos2);
    }
}
