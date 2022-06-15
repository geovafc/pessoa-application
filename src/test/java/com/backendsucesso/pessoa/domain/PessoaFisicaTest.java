package com.backendsucesso.pessoa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.backendsucesso.pessoa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PessoaFisicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PessoaFisica.class);
        PessoaFisica pessoaFisica1 = new PessoaFisica();
        pessoaFisica1.setId(1L);
        PessoaFisica pessoaFisica2 = new PessoaFisica();
        pessoaFisica2.setId(pessoaFisica1.getId());
        assertThat(pessoaFisica1).isEqualTo(pessoaFisica2);
        pessoaFisica2.setId(2L);
        assertThat(pessoaFisica1).isNotEqualTo(pessoaFisica2);
        pessoaFisica1.setId(null);
        assertThat(pessoaFisica1).isNotEqualTo(pessoaFisica2);
    }
}
