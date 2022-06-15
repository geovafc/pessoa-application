package com.backendsucesso.pessoa.web.rest;

import com.backendsucesso.pessoa.domain.PessoaFisica;
import com.backendsucesso.pessoa.repository.PessoaFisicaRepository;
import com.backendsucesso.pessoa.service.PessoaFisicaService;
import com.backendsucesso.pessoa.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.backendsucesso.pessoa.domain.PessoaFisica}.
 */
@RestController
@RequestMapping("/api")
public class PessoaFisicaResource {

    private final Logger log = LoggerFactory.getLogger(PessoaFisicaResource.class);

    private static final String ENTITY_NAME = "pessoaApplicationPessoaFisica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PessoaFisicaService pessoaFisicaService;

    private final PessoaFisicaRepository pessoaFisicaRepository;

    public PessoaFisicaResource(PessoaFisicaService pessoaFisicaService, PessoaFisicaRepository pessoaFisicaRepository) {
        this.pessoaFisicaService = pessoaFisicaService;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
    }

    /**
     * {@code POST  /pessoa-fisicas} : Create a new pessoaFisica.
     *
     * @param pessoaFisica the pessoaFisica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoaFisica, or with status {@code 400 (Bad Request)} if the pessoaFisica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pessoa-fisicas")
    public ResponseEntity<PessoaFisica> createPessoaFisica(@Valid @RequestBody PessoaFisica pessoaFisica) throws URISyntaxException {
        log.debug("REST request to save PessoaFisica : {}", pessoaFisica);
        if (pessoaFisica.getId() != null) {
            throw new BadRequestAlertException("A new pessoaFisica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PessoaFisica result = pessoaFisicaService.save(pessoaFisica);
        return ResponseEntity
            .created(new URI("/api/pessoa-fisicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pessoa-fisicas/:id} : Updates an existing pessoaFisica.
     *
     * @param id the id of the pessoaFisica to save.
     * @param pessoaFisica the pessoaFisica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoaFisica,
     * or with status {@code 400 (Bad Request)} if the pessoaFisica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pessoaFisica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pessoa-fisicas/{id}")
    public ResponseEntity<PessoaFisica> updatePessoaFisica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PessoaFisica pessoaFisica
    ) throws URISyntaxException {
        log.debug("REST request to update PessoaFisica : {}, {}", id, pessoaFisica);
        if (pessoaFisica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoaFisica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaFisicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PessoaFisica result = pessoaFisicaService.update(pessoaFisica);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pessoaFisica.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pessoa-fisicas/:id} : Partial updates given fields of an existing pessoaFisica, field will ignore if it is null
     *
     * @param id the id of the pessoaFisica to save.
     * @param pessoaFisica the pessoaFisica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoaFisica,
     * or with status {@code 400 (Bad Request)} if the pessoaFisica is not valid,
     * or with status {@code 404 (Not Found)} if the pessoaFisica is not found,
     * or with status {@code 500 (Internal Server Error)} if the pessoaFisica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pessoa-fisicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PessoaFisica> partialUpdatePessoaFisica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PessoaFisica pessoaFisica
    ) throws URISyntaxException {
        log.debug("REST request to partial update PessoaFisica partially : {}, {}", id, pessoaFisica);
        if (pessoaFisica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoaFisica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoaFisicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PessoaFisica> result = pessoaFisicaService.partialUpdate(pessoaFisica);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pessoaFisica.getId().toString())
        );
    }

    /**
     * {@code GET  /pessoa-fisicas} : get all the pessoaFisicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pessoaFisicas in body.
     */
    @GetMapping("/pessoa-fisicas")
    public ResponseEntity<List<PessoaFisica>> getAllPessoaFisicas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PessoaFisicas");
        Page<PessoaFisica> page = pessoaFisicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pessoa-fisicas/:id} : get the "id" pessoaFisica.
     *
     * @param id the id of the pessoaFisica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pessoaFisica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pessoa-fisicas/{id}")
    public ResponseEntity<PessoaFisica> getPessoaFisica(@PathVariable Long id) {
        log.debug("REST request to get PessoaFisica : {}", id);
        Optional<PessoaFisica> pessoaFisica = pessoaFisicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pessoaFisica);
    }

    /**
     * {@code DELETE  /pessoa-fisicas/:id} : delete the "id" pessoaFisica.
     *
     * @param id the id of the pessoaFisica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pessoa-fisicas/{id}")
    public ResponseEntity<Void> deletePessoaFisica(@PathVariable Long id) {
        log.debug("REST request to delete PessoaFisica : {}", id);
        pessoaFisicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}