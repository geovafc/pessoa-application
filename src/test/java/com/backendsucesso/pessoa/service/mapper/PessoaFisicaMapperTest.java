package com.backendsucesso.pessoa.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PessoaFisicaMapperTest {

    private PessoaFisicaMapper pessoaFisicaMapper;

    @BeforeEach
    public void setUp() {
        pessoaFisicaMapper = new PessoaFisicaMapperImpl();
    }
}
