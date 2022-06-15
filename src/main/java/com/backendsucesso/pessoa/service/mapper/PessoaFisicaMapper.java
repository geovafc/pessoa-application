package com.backendsucesso.pessoa.service.mapper;

import com.backendsucesso.pessoa.domain.PessoaFisica;
import com.backendsucesso.pessoa.service.dto.PessoaFisicaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PessoaFisica} and its DTO {@link PessoaFisicaDTO}.
 */
@Mapper(componentModel = "spring")
public interface PessoaFisicaMapper extends EntityMapper<PessoaFisicaDTO, PessoaFisica> {}
