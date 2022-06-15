package com.backendsucesso.pessoa.repository;

import com.backendsucesso.pessoa.domain.PessoaFisica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PessoaFisica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {}
