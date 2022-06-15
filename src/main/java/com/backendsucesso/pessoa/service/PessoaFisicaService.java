package com.backendsucesso.pessoa.service;

import com.backendsucesso.pessoa.domain.PessoaFisica;
import com.backendsucesso.pessoa.repository.PessoaFisicaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PessoaFisica}.
 */
@Service
@Transactional
public class PessoaFisicaService {

    private final Logger log = LoggerFactory.getLogger(PessoaFisicaService.class);

    private final PessoaFisicaRepository pessoaFisicaRepository;

    public PessoaFisicaService(PessoaFisicaRepository pessoaFisicaRepository) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }

    /**
     * Save a pessoaFisica.
     *
     * @param pessoaFisica the entity to save.
     * @return the persisted entity.
     */
    public PessoaFisica save(PessoaFisica pessoaFisica) {
        log.debug("Request to save PessoaFisica : {}", pessoaFisica);
        return pessoaFisicaRepository.save(pessoaFisica);
    }

    /**
     * Update a pessoaFisica.
     *
     * @param pessoaFisica the entity to save.
     * @return the persisted entity.
     */
    public PessoaFisica update(PessoaFisica pessoaFisica) {
        log.debug("Request to save PessoaFisica : {}", pessoaFisica);
        return pessoaFisicaRepository.save(pessoaFisica);
    }

    /**
     * Partially update a pessoaFisica.
     *
     * @param pessoaFisica the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PessoaFisica> partialUpdate(PessoaFisica pessoaFisica) {
        log.debug("Request to partially update PessoaFisica : {}", pessoaFisica);

        return pessoaFisicaRepository
            .findById(pessoaFisica.getId())
            .map(existingPessoaFisica -> {
                if (pessoaFisica.getNome() != null) {
                    existingPessoaFisica.setNome(pessoaFisica.getNome());
                }
                if (pessoaFisica.getCpf() != null) {
                    existingPessoaFisica.setCpf(pessoaFisica.getCpf());
                }
                if (pessoaFisica.getIdade() != null) {
                    existingPessoaFisica.setIdade(pessoaFisica.getIdade());
                }
                if (pessoaFisica.getEmail() != null) {
                    existingPessoaFisica.setEmail(pessoaFisica.getEmail());
                }
                if (pessoaFisica.getTelefone() != null) {
                    existingPessoaFisica.setTelefone(pessoaFisica.getTelefone());
                }

                return existingPessoaFisica;
            })
            .map(pessoaFisicaRepository::save);
    }

    /**
     * Get all the pessoaFisicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PessoaFisica> findAll(Pageable pageable) {
        log.debug("Request to get all PessoaFisicas");
        return pessoaFisicaRepository.findAll(pageable);
    }

    /**
     * Get one pessoaFisica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PessoaFisica> findOne(Long id) {
        log.debug("Request to get PessoaFisica : {}", id);
        return pessoaFisicaRepository.findById(id);
    }

    /**
     * Delete the pessoaFisica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PessoaFisica : {}", id);
        pessoaFisicaRepository.deleteById(id);
    }
}
