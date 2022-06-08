package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.RecursosHumanos;
import com.mycompany.myapp.repository.RecursosHumanosRepository;
import com.mycompany.myapp.service.RecursosHumanosService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link RecursosHumanos}.
 */
@Service
public class RecursosHumanosServiceImpl implements RecursosHumanosService {

    private final Logger log = LoggerFactory.getLogger(RecursosHumanosServiceImpl.class);

    private final RecursosHumanosRepository recursosHumanosRepository;

    public RecursosHumanosServiceImpl(RecursosHumanosRepository recursosHumanosRepository) {
        this.recursosHumanosRepository = recursosHumanosRepository;
    }

    @Override
    public RecursosHumanos save(RecursosHumanos recursosHumanos) {
        log.debug("Request to save RecursosHumanos : {}", recursosHumanos);
        return recursosHumanosRepository.save(recursosHumanos);
    }

    @Override
    public RecursosHumanos update(RecursosHumanos recursosHumanos) {
        log.debug("Request to save RecursosHumanos : {}", recursosHumanos);
        return recursosHumanosRepository.save(recursosHumanos);
    }

    @Override
    public Optional<RecursosHumanos> partialUpdate(RecursosHumanos recursosHumanos) {
        log.debug("Request to partially update RecursosHumanos : {}", recursosHumanos);

        return recursosHumanosRepository
            .findById(recursosHumanos.getId())
            .map(existingRecursosHumanos -> {
                if (recursosHumanos.getNombre() != null) {
                    existingRecursosHumanos.setNombre(recursosHumanos.getNombre());
                }
                if (recursosHumanos.getApellidoPaterno() != null) {
                    existingRecursosHumanos.setApellidoPaterno(recursosHumanos.getApellidoPaterno());
                }
                if (recursosHumanos.getApellidoMaterno() != null) {
                    existingRecursosHumanos.setApellidoMaterno(recursosHumanos.getApellidoMaterno());
                }
                if (recursosHumanos.getFechaNacimiento() != null) {
                    existingRecursosHumanos.setFechaNacimiento(recursosHumanos.getFechaNacimiento());
                }
                if (recursosHumanos.getPuesto() != null) {
                    existingRecursosHumanos.setPuesto(recursosHumanos.getPuesto());
                }

                return existingRecursosHumanos;
            })
            .map(recursosHumanosRepository::save);
    }

    @Override
    public Page<RecursosHumanos> findAll(Pageable pageable) {
        log.debug("Request to get all RecursosHumanos");
        return recursosHumanosRepository.findAll(pageable);
    }

    @Override
    public Optional<RecursosHumanos> findOne(String id) {
        log.debug("Request to get RecursosHumanos : {}", id);
        return recursosHumanosRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete RecursosHumanos : {}", id);
        recursosHumanosRepository.deleteById(id);
    }
}
