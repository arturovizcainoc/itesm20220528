package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.RecursosHumanos;
import com.mycompany.myapp.repository.RecursosHumanosRepository;
import com.mycompany.myapp.service.RecursosHumanosService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.RecursosHumanos}.
 */
@RestController
@RequestMapping("/api")
public class RecursosHumanosResource {

    private final Logger log = LoggerFactory.getLogger(RecursosHumanosResource.class);

    private static final String ENTITY_NAME = "recursosHumanos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecursosHumanosService recursosHumanosService;

    private final RecursosHumanosRepository recursosHumanosRepository;

    public RecursosHumanosResource(RecursosHumanosService recursosHumanosService, RecursosHumanosRepository recursosHumanosRepository) {
        this.recursosHumanosService = recursosHumanosService;
        this.recursosHumanosRepository = recursosHumanosRepository;
    }

    /**
     * {@code POST  /recursos-humanos} : Create a new recursosHumanos.
     *
     * @param recursosHumanos the recursosHumanos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recursosHumanos, or with status {@code 400 (Bad Request)} if the recursosHumanos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/recursos-humanos")
    public ResponseEntity<RecursosHumanos> createRecursosHumanos(@RequestBody RecursosHumanos recursosHumanos) throws URISyntaxException {
        log.debug("REST request to save RecursosHumanos : {}", recursosHumanos);
        if (recursosHumanos.getId() != null) {
            throw new BadRequestAlertException("A new recursosHumanos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecursosHumanos result = recursosHumanosService.save(recursosHumanos);
        return ResponseEntity
            .created(new URI("/api/recursos-humanos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /recursos-humanos/:id} : Updates an existing recursosHumanos.
     *
     * @param id the id of the recursosHumanos to save.
     * @param recursosHumanos the recursosHumanos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recursosHumanos,
     * or with status {@code 400 (Bad Request)} if the recursosHumanos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recursosHumanos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/recursos-humanos/{id}")
    public ResponseEntity<RecursosHumanos> updateRecursosHumanos(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RecursosHumanos recursosHumanos
    ) throws URISyntaxException {
        log.debug("REST request to update RecursosHumanos : {}, {}", id, recursosHumanos);
        if (recursosHumanos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recursosHumanos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recursosHumanosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecursosHumanos result = recursosHumanosService.update(recursosHumanos);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recursosHumanos.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /recursos-humanos/:id} : Partial updates given fields of an existing recursosHumanos, field will ignore if it is null
     *
     * @param id the id of the recursosHumanos to save.
     * @param recursosHumanos the recursosHumanos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recursosHumanos,
     * or with status {@code 400 (Bad Request)} if the recursosHumanos is not valid,
     * or with status {@code 404 (Not Found)} if the recursosHumanos is not found,
     * or with status {@code 500 (Internal Server Error)} if the recursosHumanos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/recursos-humanos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecursosHumanos> partialUpdateRecursosHumanos(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RecursosHumanos recursosHumanos
    ) throws URISyntaxException {
        log.debug("REST request to partial update RecursosHumanos partially : {}, {}", id, recursosHumanos);
        if (recursosHumanos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recursosHumanos.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recursosHumanosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecursosHumanos> result = recursosHumanosService.partialUpdate(recursosHumanos);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recursosHumanos.getId())
        );
    }

    /**
     * {@code GET  /recursos-humanos} : get all the recursosHumanos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recursosHumanos in body.
     */
    @GetMapping("/recursos-humanos")
    public ResponseEntity<List<RecursosHumanos>> getAllRecursosHumanos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RecursosHumanos");
        Page<RecursosHumanos> page = recursosHumanosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /recursos-humanos/:id} : get the "id" recursosHumanos.
     *
     * @param id the id of the recursosHumanos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recursosHumanos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recursos-humanos/{id}")
    public ResponseEntity<RecursosHumanos> getRecursosHumanos(@PathVariable String id) {
        log.debug("REST request to get RecursosHumanos : {}", id);
        Optional<RecursosHumanos> recursosHumanos = recursosHumanosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recursosHumanos);
    }

    /**
     * {@code DELETE  /recursos-humanos/:id} : delete the "id" recursosHumanos.
     *
     * @param id the id of the recursosHumanos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recursos-humanos/{id}")
    public ResponseEntity<Void> deleteRecursosHumanos(@PathVariable String id) {
        log.debug("REST request to delete RecursosHumanos : {}", id);
        recursosHumanosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
