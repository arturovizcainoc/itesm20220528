package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.RecursosHumanos;
import com.mycompany.myapp.domain.enumeration.tipoPuesto;
import com.mycompany.myapp.repository.RecursosHumanosRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link RecursosHumanosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecursosHumanosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_MATERNO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final tipoPuesto DEFAULT_PUESTO = tipoPuesto.DESARROLLADOR;
    private static final tipoPuesto UPDATED_PUESTO = tipoPuesto.ADMINISTRADOR;

    private static final String ENTITY_API_URL = "/api/recursos-humanos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RecursosHumanosRepository recursosHumanosRepository;

    @Autowired
    private MockMvc restRecursosHumanosMockMvc;

    private RecursosHumanos recursosHumanos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecursosHumanos createEntity() {
        RecursosHumanos recursosHumanos = new RecursosHumanos()
            .nombre(DEFAULT_NOMBRE)
            .apellidoPaterno(DEFAULT_APELLIDO_PATERNO)
            .apellidoMaterno(DEFAULT_APELLIDO_MATERNO)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO)
            .puesto(DEFAULT_PUESTO);
        return recursosHumanos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecursosHumanos createUpdatedEntity() {
        RecursosHumanos recursosHumanos = new RecursosHumanos()
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .puesto(UPDATED_PUESTO);
        return recursosHumanos;
    }

    @BeforeEach
    public void initTest() {
        recursosHumanosRepository.deleteAll();
        recursosHumanos = createEntity();
    }

    @Test
    void createRecursosHumanos() throws Exception {
        int databaseSizeBeforeCreate = recursosHumanosRepository.findAll().size();
        // Create the RecursosHumanos
        restRecursosHumanosMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recursosHumanos))
            )
            .andExpect(status().isCreated());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeCreate + 1);
        RecursosHumanos testRecursosHumanos = recursosHumanosList.get(recursosHumanosList.size() - 1);
        assertThat(testRecursosHumanos.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRecursosHumanos.getApellidoPaterno()).isEqualTo(DEFAULT_APELLIDO_PATERNO);
        assertThat(testRecursosHumanos.getApellidoMaterno()).isEqualTo(DEFAULT_APELLIDO_MATERNO);
        assertThat(testRecursosHumanos.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testRecursosHumanos.getPuesto()).isEqualTo(DEFAULT_PUESTO);
    }

    @Test
    void createRecursosHumanosWithExistingId() throws Exception {
        // Create the RecursosHumanos with an existing ID
        recursosHumanos.setId("existing_id");

        int databaseSizeBeforeCreate = recursosHumanosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecursosHumanosMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recursosHumanos))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRecursosHumanos() throws Exception {
        // Initialize the database
        recursosHumanosRepository.save(recursosHumanos);

        // Get all the recursosHumanosList
        restRecursosHumanosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recursosHumanos.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidoPaterno").value(hasItem(DEFAULT_APELLIDO_PATERNO)))
            .andExpect(jsonPath("$.[*].apellidoMaterno").value(hasItem(DEFAULT_APELLIDO_MATERNO)))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].puesto").value(hasItem(DEFAULT_PUESTO.toString())));
    }

    @Test
    void getRecursosHumanos() throws Exception {
        // Initialize the database
        recursosHumanosRepository.save(recursosHumanos);

        // Get the recursosHumanos
        restRecursosHumanosMockMvc
            .perform(get(ENTITY_API_URL_ID, recursosHumanos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recursosHumanos.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellidoPaterno").value(DEFAULT_APELLIDO_PATERNO))
            .andExpect(jsonPath("$.apellidoMaterno").value(DEFAULT_APELLIDO_MATERNO))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()))
            .andExpect(jsonPath("$.puesto").value(DEFAULT_PUESTO.toString()));
    }

    @Test
    void getNonExistingRecursosHumanos() throws Exception {
        // Get the recursosHumanos
        restRecursosHumanosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewRecursosHumanos() throws Exception {
        // Initialize the database
        recursosHumanosRepository.save(recursosHumanos);

        int databaseSizeBeforeUpdate = recursosHumanosRepository.findAll().size();

        // Update the recursosHumanos
        RecursosHumanos updatedRecursosHumanos = recursosHumanosRepository.findById(recursosHumanos.getId()).get();
        updatedRecursosHumanos
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .puesto(UPDATED_PUESTO);

        restRecursosHumanosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecursosHumanos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRecursosHumanos))
            )
            .andExpect(status().isOk());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeUpdate);
        RecursosHumanos testRecursosHumanos = recursosHumanosList.get(recursosHumanosList.size() - 1);
        assertThat(testRecursosHumanos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRecursosHumanos.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testRecursosHumanos.getApellidoMaterno()).isEqualTo(UPDATED_APELLIDO_MATERNO);
        assertThat(testRecursosHumanos.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testRecursosHumanos.getPuesto()).isEqualTo(UPDATED_PUESTO);
    }

    @Test
    void putNonExistingRecursosHumanos() throws Exception {
        int databaseSizeBeforeUpdate = recursosHumanosRepository.findAll().size();
        recursosHumanos.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursosHumanosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, recursosHumanos.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursosHumanos))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRecursosHumanos() throws Exception {
        int databaseSizeBeforeUpdate = recursosHumanosRepository.findAll().size();
        recursosHumanos.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursosHumanosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(recursosHumanos))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRecursosHumanos() throws Exception {
        int databaseSizeBeforeUpdate = recursosHumanosRepository.findAll().size();
        recursosHumanos.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursosHumanosMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(recursosHumanos))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRecursosHumanosWithPatch() throws Exception {
        // Initialize the database
        recursosHumanosRepository.save(recursosHumanos);

        int databaseSizeBeforeUpdate = recursosHumanosRepository.findAll().size();

        // Update the recursosHumanos using partial update
        RecursosHumanos partialUpdatedRecursosHumanos = new RecursosHumanos();
        partialUpdatedRecursosHumanos.setId(recursosHumanos.getId());

        partialUpdatedRecursosHumanos.nombre(UPDATED_NOMBRE).apellidoMaterno(UPDATED_APELLIDO_MATERNO);

        restRecursosHumanosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecursosHumanos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecursosHumanos))
            )
            .andExpect(status().isOk());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeUpdate);
        RecursosHumanos testRecursosHumanos = recursosHumanosList.get(recursosHumanosList.size() - 1);
        assertThat(testRecursosHumanos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRecursosHumanos.getApellidoPaterno()).isEqualTo(DEFAULT_APELLIDO_PATERNO);
        assertThat(testRecursosHumanos.getApellidoMaterno()).isEqualTo(UPDATED_APELLIDO_MATERNO);
        assertThat(testRecursosHumanos.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
        assertThat(testRecursosHumanos.getPuesto()).isEqualTo(DEFAULT_PUESTO);
    }

    @Test
    void fullUpdateRecursosHumanosWithPatch() throws Exception {
        // Initialize the database
        recursosHumanosRepository.save(recursosHumanos);

        int databaseSizeBeforeUpdate = recursosHumanosRepository.findAll().size();

        // Update the recursosHumanos using partial update
        RecursosHumanos partialUpdatedRecursosHumanos = new RecursosHumanos();
        partialUpdatedRecursosHumanos.setId(recursosHumanos.getId());

        partialUpdatedRecursosHumanos
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO)
            .puesto(UPDATED_PUESTO);

        restRecursosHumanosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecursosHumanos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRecursosHumanos))
            )
            .andExpect(status().isOk());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeUpdate);
        RecursosHumanos testRecursosHumanos = recursosHumanosList.get(recursosHumanosList.size() - 1);
        assertThat(testRecursosHumanos.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRecursosHumanos.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testRecursosHumanos.getApellidoMaterno()).isEqualTo(UPDATED_APELLIDO_MATERNO);
        assertThat(testRecursosHumanos.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
        assertThat(testRecursosHumanos.getPuesto()).isEqualTo(UPDATED_PUESTO);
    }

    @Test
    void patchNonExistingRecursosHumanos() throws Exception {
        int databaseSizeBeforeUpdate = recursosHumanosRepository.findAll().size();
        recursosHumanos.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecursosHumanosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recursosHumanos.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recursosHumanos))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRecursosHumanos() throws Exception {
        int databaseSizeBeforeUpdate = recursosHumanosRepository.findAll().size();
        recursosHumanos.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursosHumanosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recursosHumanos))
            )
            .andExpect(status().isBadRequest());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRecursosHumanos() throws Exception {
        int databaseSizeBeforeUpdate = recursosHumanosRepository.findAll().size();
        recursosHumanos.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecursosHumanosMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(recursosHumanos))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RecursosHumanos in the database
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRecursosHumanos() throws Exception {
        // Initialize the database
        recursosHumanosRepository.save(recursosHumanos);

        int databaseSizeBeforeDelete = recursosHumanosRepository.findAll().size();

        // Delete the recursosHumanos
        restRecursosHumanosMockMvc
            .perform(delete(ENTITY_API_URL_ID, recursosHumanos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RecursosHumanos> recursosHumanosList = recursosHumanosRepository.findAll();
        assertThat(recursosHumanosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
