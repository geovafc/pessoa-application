package com.backendsucesso.pessoa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.backendsucesso.pessoa.IntegrationTest;
import com.backendsucesso.pessoa.domain.PessoaFisica;
import com.backendsucesso.pessoa.repository.PessoaFisicaRepository;
import com.backendsucesso.pessoa.service.dto.PessoaFisicaDTO;
import com.backendsucesso.pessoa.service.mapper.PessoaFisicaMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PessoaFisicaResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PessoaFisicaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_IDADE = "AAAAAAAAAA";
    private static final String UPDATED_IDADE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pessoa-fisicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Autowired
    private PessoaFisicaMapper pessoaFisicaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPessoaFisicaMockMvc;

    private PessoaFisica pessoaFisica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PessoaFisica createEntity(EntityManager em) {
        PessoaFisica pessoaFisica = new PessoaFisica()
            .nome(DEFAULT_NOME)
            .cpf(DEFAULT_CPF)
            .idade(DEFAULT_IDADE)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE);
        return pessoaFisica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PessoaFisica createUpdatedEntity(EntityManager em) {
        PessoaFisica pessoaFisica = new PessoaFisica()
            .nome(UPDATED_NOME)
            .cpf(UPDATED_CPF)
            .idade(UPDATED_IDADE)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);
        return pessoaFisica;
    }

    @BeforeEach
    public void initTest() {
        pessoaFisica = createEntity(em);
    }

    @Test
    @Transactional
    void createPessoaFisica() throws Exception {
        int databaseSizeBeforeCreate = pessoaFisicaRepository.findAll().size();
        // Create the PessoaFisica
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);
        restPessoaFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeCreate + 1);
        PessoaFisica testPessoaFisica = pessoaFisicaList.get(pessoaFisicaList.size() - 1);
        assertThat(testPessoaFisica.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPessoaFisica.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testPessoaFisica.getIdade()).isEqualTo(DEFAULT_IDADE);
        assertThat(testPessoaFisica.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPessoaFisica.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    void createPessoaFisicaWithExistingId() throws Exception {
        // Create the PessoaFisica with an existing ID
        pessoaFisica.setId(1L);
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        int databaseSizeBeforeCreate = pessoaFisicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaFisicaRepository.findAll().size();
        // set the field null
        pessoaFisica.setNome(null);

        // Create the PessoaFisica, which fails.
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        restPessoaFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpfIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaFisicaRepository.findAll().size();
        // set the field null
        pessoaFisica.setCpf(null);

        // Create the PessoaFisica, which fails.
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        restPessoaFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaFisicaRepository.findAll().size();
        // set the field null
        pessoaFisica.setIdade(null);

        // Create the PessoaFisica, which fails.
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        restPessoaFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaFisicaRepository.findAll().size();
        // set the field null
        pessoaFisica.setEmail(null);

        // Create the PessoaFisica, which fails.
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        restPessoaFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = pessoaFisicaRepository.findAll().size();
        // set the field null
        pessoaFisica.setTelefone(null);

        // Create the PessoaFisica, which fails.
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        restPessoaFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPessoaFisicas() throws Exception {
        // Initialize the database
        pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        // Get all the pessoaFisicaList
        restPessoaFisicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoaFisica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].idade").value(hasItem(DEFAULT_IDADE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)));
    }

    @Test
    @Transactional
    void getPessoaFisica() throws Exception {
        // Initialize the database
        pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        // Get the pessoaFisica
        restPessoaFisicaMockMvc
            .perform(get(ENTITY_API_URL_ID, pessoaFisica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pessoaFisica.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.idade").value(DEFAULT_IDADE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE));
    }

    @Test
    @Transactional
    void getNonExistingPessoaFisica() throws Exception {
        // Get the pessoaFisica
        restPessoaFisicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPessoaFisica() throws Exception {
        // Initialize the database
        pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        int databaseSizeBeforeUpdate = pessoaFisicaRepository.findAll().size();

        // Update the pessoaFisica
        PessoaFisica updatedPessoaFisica = pessoaFisicaRepository.findById(pessoaFisica.getId()).get();
        // Disconnect from session so that the updates on updatedPessoaFisica are not directly saved in db
        em.detach(updatedPessoaFisica);
        updatedPessoaFisica.nome(UPDATED_NOME).cpf(UPDATED_CPF).idade(UPDATED_IDADE).email(UPDATED_EMAIL).telefone(UPDATED_TELEFONE);
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(updatedPessoaFisica);

        restPessoaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaFisicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isOk());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeUpdate);
        PessoaFisica testPessoaFisica = pessoaFisicaList.get(pessoaFisicaList.size() - 1);
        assertThat(testPessoaFisica.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoaFisica.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testPessoaFisica.getIdade()).isEqualTo(UPDATED_IDADE);
        assertThat(testPessoaFisica.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPessoaFisica.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void putNonExistingPessoaFisica() throws Exception {
        int databaseSizeBeforeUpdate = pessoaFisicaRepository.findAll().size();
        pessoaFisica.setId(count.incrementAndGet());

        // Create the PessoaFisica
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pessoaFisicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPessoaFisica() throws Exception {
        int databaseSizeBeforeUpdate = pessoaFisicaRepository.findAll().size();
        pessoaFisica.setId(count.incrementAndGet());

        // Create the PessoaFisica
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPessoaFisica() throws Exception {
        int databaseSizeBeforeUpdate = pessoaFisicaRepository.findAll().size();
        pessoaFisica.setId(count.incrementAndGet());

        // Create the PessoaFisica
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePessoaFisicaWithPatch() throws Exception {
        // Initialize the database
        pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        int databaseSizeBeforeUpdate = pessoaFisicaRepository.findAll().size();

        // Update the pessoaFisica using partial update
        PessoaFisica partialUpdatedPessoaFisica = new PessoaFisica();
        partialUpdatedPessoaFisica.setId(pessoaFisica.getId());

        partialUpdatedPessoaFisica.nome(UPDATED_NOME).email(UPDATED_EMAIL).telefone(UPDATED_TELEFONE);

        restPessoaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoaFisica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPessoaFisica))
            )
            .andExpect(status().isOk());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeUpdate);
        PessoaFisica testPessoaFisica = pessoaFisicaList.get(pessoaFisicaList.size() - 1);
        assertThat(testPessoaFisica.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoaFisica.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testPessoaFisica.getIdade()).isEqualTo(DEFAULT_IDADE);
        assertThat(testPessoaFisica.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPessoaFisica.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void fullUpdatePessoaFisicaWithPatch() throws Exception {
        // Initialize the database
        pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        int databaseSizeBeforeUpdate = pessoaFisicaRepository.findAll().size();

        // Update the pessoaFisica using partial update
        PessoaFisica partialUpdatedPessoaFisica = new PessoaFisica();
        partialUpdatedPessoaFisica.setId(pessoaFisica.getId());

        partialUpdatedPessoaFisica.nome(UPDATED_NOME).cpf(UPDATED_CPF).idade(UPDATED_IDADE).email(UPDATED_EMAIL).telefone(UPDATED_TELEFONE);

        restPessoaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPessoaFisica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPessoaFisica))
            )
            .andExpect(status().isOk());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeUpdate);
        PessoaFisica testPessoaFisica = pessoaFisicaList.get(pessoaFisicaList.size() - 1);
        assertThat(testPessoaFisica.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPessoaFisica.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testPessoaFisica.getIdade()).isEqualTo(UPDATED_IDADE);
        assertThat(testPessoaFisica.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPessoaFisica.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void patchNonExistingPessoaFisica() throws Exception {
        int databaseSizeBeforeUpdate = pessoaFisicaRepository.findAll().size();
        pessoaFisica.setId(count.incrementAndGet());

        // Create the PessoaFisica
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pessoaFisicaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPessoaFisica() throws Exception {
        int databaseSizeBeforeUpdate = pessoaFisicaRepository.findAll().size();
        pessoaFisica.setId(count.incrementAndGet());

        // Create the PessoaFisica
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPessoaFisica() throws Exception {
        int databaseSizeBeforeUpdate = pessoaFisicaRepository.findAll().size();
        pessoaFisica.setId(count.incrementAndGet());

        // Create the PessoaFisica
        PessoaFisicaDTO pessoaFisicaDTO = pessoaFisicaMapper.toDto(pessoaFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPessoaFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pessoaFisicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PessoaFisica in the database
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePessoaFisica() throws Exception {
        // Initialize the database
        pessoaFisicaRepository.saveAndFlush(pessoaFisica);

        int databaseSizeBeforeDelete = pessoaFisicaRepository.findAll().size();

        // Delete the pessoaFisica
        restPessoaFisicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, pessoaFisica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PessoaFisica> pessoaFisicaList = pessoaFisicaRepository.findAll();
        assertThat(pessoaFisicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
