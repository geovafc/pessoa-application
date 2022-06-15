package com.backendsucesso.pessoa.service;

import com.backendsucesso.pessoa.domain.PessoaFisica;
import com.backendsucesso.pessoa.repository.PessoaFisicaRepository;
import com.backendsucesso.pessoa.service.dto.PessoaFisicaDTO;
import com.backendsucesso.pessoa.service.mapper.PessoaFisicaMapper;
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

    private final PessoaFisicaMapper pessoaFisicaMapper;

    public PessoaFisicaService(PessoaFisicaRepository pessoaFisicaRepository, PessoaFisicaMapper pessoaFisicaMapper) {
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.pessoaFisicaMapper = pessoaFisicaMapper;
    }

    /**
     * Save a pessoaFisica.
     *
     * @param pessoaFisicaDTO the entity to save.
     * @return the persisted entity.
     */
    public PessoaFisicaDTO save(PessoaFisicaDTO pessoaFisicaDTO) {
        log.debug("Request to save PessoaFisica : {}", pessoaFisicaDTO);
        PessoaFisica pessoaFisica = pessoaFisicaMapper.toEntity(pessoaFisicaDTO);
        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
        return pessoaFisicaMapper.toDto(pessoaFisica);
    }

    /**
     * Update a pessoaFisica.
     *
     * @param pessoaFisicaDTO the entity to save.
     * @return the persisted entity.
     */
    public PessoaFisicaDTO update(PessoaFisicaDTO pessoaFisicaDTO) {
        log.debug("Request to save PessoaFisica : {}", pessoaFisicaDTO);
        PessoaFisica pessoaFisica = pessoaFisicaMapper.toEntity(pessoaFisicaDTO);
        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
        return pessoaFisicaMapper.toDto(pessoaFisica);
    }

    /**
     * Partially update a pessoaFisica.
     *
     * @param pessoaFisicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PessoaFisicaDTO> partialUpdate(PessoaFisicaDTO pessoaFisicaDTO) {
        log.debug("Request to partially update PessoaFisica : {}", pessoaFisicaDTO);

        return pessoaFisicaRepository
            .findById(pessoaFisicaDTO.getId())
            .map(existingPessoaFisica -> {
                pessoaFisicaMapper.partialUpdate(existingPessoaFisica, pessoaFisicaDTO);

                return existingPessoaFisica;
            })
            .map(pessoaFisicaRepository::save)
            .map(pessoaFisicaMapper::toDto);
    }

    /**
     * Get all the pessoaFisicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PessoaFisicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PessoaFisicas");
        return pessoaFisicaRepository.findAll(pageable).map(pessoaFisicaMapper::toDto);
    }

    /**
     * Get one pessoaFisica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PessoaFisicaDTO> findOne(Long id) {
        log.debug("Request to get PessoaFisica : {}", id);
        return pessoaFisicaRepository.findById(id).map(pessoaFisicaMapper::toDto);
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
