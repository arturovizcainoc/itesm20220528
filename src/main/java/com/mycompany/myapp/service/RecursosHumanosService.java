package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.RecursosHumanos;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link RecursosHumanos}.
 */
public interface RecursosHumanosService {
    /**
     * Save a recursosHumanos.
     *
     * @param recursosHumanos the entity to save.
     * @return the persisted entity.
     */
    RecursosHumanos save(RecursosHumanos recursosHumanos);

    /**
     * Updates a recursosHumanos.
     *
     * @param recursosHumanos the entity to update.
     * @return the persisted entity.
     */
    RecursosHumanos update(RecursosHumanos recursosHumanos);

    /**
     * Partially updates a recursosHumanos.
     *
     * @param recursosHumanos the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecursosHumanos> partialUpdate(RecursosHumanos recursosHumanos);

    /**
     * Get all the recursosHumanos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RecursosHumanos> findAll(Pageable pageable);

    /**
     * Get the "id" recursosHumanos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecursosHumanos> findOne(String id);

    /**
     * Delete the "id" recursosHumanos.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
