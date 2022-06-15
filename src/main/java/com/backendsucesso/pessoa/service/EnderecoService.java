package com.backendsucesso.pessoa.service;

import com.backendsucesso.pessoa.domain.Endereco;
import com.backendsucesso.pessoa.repository.EnderecoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Endereco}.
 */
@Service
@Transactional
public class EnderecoService {

    private final Logger log = LoggerFactory.getLogger(EnderecoService.class);

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    /**
     * Save a endereco.
     *
     * @param endereco the entity to save.
     * @return the persisted entity.
     */
    public Endereco save(Endereco endereco) {
        log.debug("Request to save Endereco : {}", endereco);
        return enderecoRepository.save(endereco);
    }

    /**
     * Update a endereco.
     *
     * @param endereco the entity to save.
     * @return the persisted entity.
     */
    public Endereco update(Endereco endereco) {
        log.debug("Request to save Endereco : {}", endereco);
        return enderecoRepository.save(endereco);
    }

    /**
     * Partially update a endereco.
     *
     * @param endereco the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Endereco> partialUpdate(Endereco endereco) {
        log.debug("Request to partially update Endereco : {}", endereco);

        return enderecoRepository
            .findById(endereco.getId())
            .map(existingEndereco -> {
                if (endereco.getLogradouro() != null) {
                    existingEndereco.setLogradouro(endereco.getLogradouro());
                }
                if (endereco.getCep() != null) {
                    existingEndereco.setCep(endereco.getCep());
                }
                if (endereco.getCidade() != null) {
                    existingEndereco.setCidade(endereco.getCidade());
                }
                if (endereco.getEstado() != null) {
                    existingEndereco.setEstado(endereco.getEstado());
                }

                return existingEndereco;
            })
            .map(enderecoRepository::save);
    }

    /**
     * Get all the enderecos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Endereco> findAll() {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll();
    }

    /**
     * Get one endereco by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Endereco> findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        return enderecoRepository.findById(id);
    }

    /**
     * Delete the endereco by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.deleteById(id);
    }
}
