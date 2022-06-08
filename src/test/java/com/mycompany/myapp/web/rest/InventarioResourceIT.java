package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Inventario;
import com.mycompany.myapp.domain.enumeration.inventarioTipo;
import com.mycompany.myapp.repository.InventarioRepository;
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
 * Integration tests for the {@link InventarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InventarioResourceIT {

    private static final String DEFAULT_PRODUCTO = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INGRESO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_SALIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SALIDA = LocalDate.now(ZoneId.systemDefault());

    private static final inventarioTipo DEFAULT_TIPO = inventarioTipo.CAJA;
    private static final inventarioTipo UPDATED_TIPO = inventarioTipo.HERRAMIENTA;

    private static final String ENTITY_API_URL = "/api/inventarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private MockMvc restInventarioMockMvc;

    private Inventario inventario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventario createEntity() {
        Inventario inventario = new Inventario()
            .producto(DEFAULT_PRODUCTO)
            .fechaIngreso(DEFAULT_FECHA_INGRESO)
            .fechaSalida(DEFAULT_FECHA_SALIDA)
            .tipo(DEFAULT_TIPO);
        return inventario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventario createUpdatedEntity() {
        Inventario inventario = new Inventario()
            .producto(UPDATED_PRODUCTO)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .tipo(UPDATED_TIPO);
        return inventario;
    }

    @BeforeEach
    public void initTest() {
        inventarioRepository.deleteAll();
        inventario = createEntity();
    }

    @Test
    void createInventario() throws Exception {
        int databaseSizeBeforeCreate = inventarioRepository.findAll().size();
        // Create the Inventario
        restInventarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventario)))
            .andExpect(status().isCreated());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeCreate + 1);
        Inventario testInventario = inventarioList.get(inventarioList.size() - 1);
        assertThat(testInventario.getProducto()).isEqualTo(DEFAULT_PRODUCTO);
        assertThat(testInventario.getFechaIngreso()).isEqualTo(DEFAULT_FECHA_INGRESO);
        assertThat(testInventario.getFechaSalida()).isEqualTo(DEFAULT_FECHA_SALIDA);
        assertThat(testInventario.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    void createInventarioWithExistingId() throws Exception {
        // Create the Inventario with an existing ID
        inventario.setId("existing_id");

        int databaseSizeBeforeCreate = inventarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventario)))
            .andExpect(status().isBadRequest());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllInventarios() throws Exception {
        // Initialize the database
        inventarioRepository.save(inventario);

        // Get all the inventarioList
        restInventarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventario.getId())))
            .andExpect(jsonPath("$.[*].producto").value(hasItem(DEFAULT_PRODUCTO)))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].fechaSalida").value(hasItem(DEFAULT_FECHA_SALIDA.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    void getInventario() throws Exception {
        // Initialize the database
        inventarioRepository.save(inventario);

        // Get the inventario
        restInventarioMockMvc
            .perform(get(ENTITY_API_URL_ID, inventario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventario.getId()))
            .andExpect(jsonPath("$.producto").value(DEFAULT_PRODUCTO))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()))
            .andExpect(jsonPath("$.fechaSalida").value(DEFAULT_FECHA_SALIDA.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    void getNonExistingInventario() throws Exception {
        // Get the inventario
        restInventarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewInventario() throws Exception {
        // Initialize the database
        inventarioRepository.save(inventario);

        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();

        // Update the inventario
        Inventario updatedInventario = inventarioRepository.findById(inventario.getId()).get();
        updatedInventario
            .producto(UPDATED_PRODUCTO)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .tipo(UPDATED_TIPO);

        restInventarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedInventario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedInventario))
            )
            .andExpect(status().isOk());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
        Inventario testInventario = inventarioList.get(inventarioList.size() - 1);
        assertThat(testInventario.getProducto()).isEqualTo(UPDATED_PRODUCTO);
        assertThat(testInventario.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testInventario.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testInventario.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    void putNonExistingInventario() throws Exception {
        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();
        inventario.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventario.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchInventario() throws Exception {
        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();
        inventario.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamInventario() throws Exception {
        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();
        inventario.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventario)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateInventarioWithPatch() throws Exception {
        // Initialize the database
        inventarioRepository.save(inventario);

        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();

        // Update the inventario using partial update
        Inventario partialUpdatedInventario = new Inventario();
        partialUpdatedInventario.setId(inventario.getId());

        partialUpdatedInventario.producto(UPDATED_PRODUCTO).fechaSalida(UPDATED_FECHA_SALIDA);

        restInventarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInventario))
            )
            .andExpect(status().isOk());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
        Inventario testInventario = inventarioList.get(inventarioList.size() - 1);
        assertThat(testInventario.getProducto()).isEqualTo(UPDATED_PRODUCTO);
        assertThat(testInventario.getFechaIngreso()).isEqualTo(DEFAULT_FECHA_INGRESO);
        assertThat(testInventario.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testInventario.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    void fullUpdateInventarioWithPatch() throws Exception {
        // Initialize the database
        inventarioRepository.save(inventario);

        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();

        // Update the inventario using partial update
        Inventario partialUpdatedInventario = new Inventario();
        partialUpdatedInventario.setId(inventario.getId());

        partialUpdatedInventario
            .producto(UPDATED_PRODUCTO)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaSalida(UPDATED_FECHA_SALIDA)
            .tipo(UPDATED_TIPO);

        restInventarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInventario))
            )
            .andExpect(status().isOk());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
        Inventario testInventario = inventarioList.get(inventarioList.size() - 1);
        assertThat(testInventario.getProducto()).isEqualTo(UPDATED_PRODUCTO);
        assertThat(testInventario.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testInventario.getFechaSalida()).isEqualTo(UPDATED_FECHA_SALIDA);
        assertThat(testInventario.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    void patchNonExistingInventario() throws Exception {
        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();
        inventario.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchInventario() throws Exception {
        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();
        inventario.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventario))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamInventario() throws Exception {
        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();
        inventario.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(inventario))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteInventario() throws Exception {
        // Initialize the database
        inventarioRepository.save(inventario);

        int databaseSizeBeforeDelete = inventarioRepository.findAll().size();

        // Delete the inventario
        restInventarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
