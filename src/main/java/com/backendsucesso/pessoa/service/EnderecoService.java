package com.backendsucesso.pessoa.service;

import com.backendsucesso.pessoa.domain.Endereco;
import com.backendsucesso.pessoa.repository.EnderecoRepository;
import com.backendsucesso.pessoa.service.dto.EnderecoDTO;
import com.backendsucesso.pessoa.service.mapper.EnderecoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final EnderecoMapper enderecoMapper;

    public EnderecoService(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    /**
     * Save a endereco.
     *
     * @param enderecoDTO the entity to save.
     * @return the persisted entity.
     */
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        log.debug("Request to save Endereco : {}", enderecoDTO);
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        return enderecoMapper.toDto(endereco);
    }

    /**
     * Update a endereco.
     *
     * @param enderecoDTO the entity to save.
     * @return the persisted entity.
     */
    public EnderecoDTO update(EnderecoDTO enderecoDTO) {
        log.debug("Request to save Endereco : {}", enderecoDTO);
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        return enderecoMapper.toDto(endereco);
    }

    /**
     * Partially update a endereco.
     *
     * @param enderecoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EnderecoDTO> partialUpdate(EnderecoDTO enderecoDTO) {
        log.debug("Request to partially update Endereco : {}", enderecoDTO);

        return enderecoRepository
            .findById(enderecoDTO.getId())
            .map(existingEndereco -> {
                enderecoMapper.partialUpdate(existingEndereco, enderecoDTO);

                return existingEndereco;
            })
            .map(enderecoRepository::save)
            .map(enderecoMapper::toDto);
    }

    /**
     * Get all the enderecos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EnderecoDTO> findAll() {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll().stream().map(enderecoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one endereco by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnderecoDTO> findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        return enderecoRepository.findById(id).map(enderecoMapper::toDto);
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
