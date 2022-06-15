package com.backendsucesso.pessoa.service.mapper;

import com.backendsucesso.pessoa.domain.Endereco;
import com.backendsucesso.pessoa.domain.PessoaFisica;
import com.backendsucesso.pessoa.service.dto.EnderecoDTO;
import com.backendsucesso.pessoa.service.dto.PessoaFisicaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Endereco} and its DTO {@link EnderecoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {
    @Mapping(target = "pessoaFisica", source = "pessoaFisica", qualifiedByName = "pessoaFisicaId")
    EnderecoDTO toDto(Endereco s);

    @Named("pessoaFisicaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PessoaFisicaDTO toDtoPessoaFisicaId(PessoaFisica pessoaFisica);
}
