package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Inventario;
import com.mycompany.myapp.repository.InventarioRepository;
import com.mycompany.myapp.service.InventarioService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Inventario}.
 */
@Service
public class InventarioServiceImpl implements InventarioService {

    private final Logger log = LoggerFactory.getLogger(InventarioServiceImpl.class);

    private final InventarioRepository inventarioRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public Inventario save(Inventario inventario) {
        log.debug("Request to save Inventario : {}", inventario);
        return inventarioRepository.save(inventario);
    }

    @Override
    public Inventario update(Inventario inventario) {
        log.debug("Request to save Inventario : {}", inventario);
        return inventarioRepository.save(inventario);
    }

    @Override
    public Optional<Inventario> partialUpdate(Inventario inventario) {
        log.debug("Request to partially update Inventario : {}", inventario);

        return inventarioRepository
            .findById(inventario.getId())
            .map(existingInventario -> {
                if (inventario.getProducto() != null) {
                    existingInventario.setProducto(inventario.getProducto());
                }
                if (inventario.getFechaIngreso() != null) {
                    existingInventario.setFechaIngreso(inventario.getFechaIngreso());
                }
                if (inventario.getFechaSalida() != null) {
                    existingInventario.setFechaSalida(inventario.getFechaSalida());
                }
                if (inventario.getTipo() != null) {
                    existingInventario.setTipo(inventario.getTipo());
                }

                return existingInventario;
            })
            .map(inventarioRepository::save);
    }

    @Override
    public Page<Inventario> findAll(Pageable pageable) {
        log.debug("Request to get all Inventarios");
        return inventarioRepository.findAll(pageable);
    }

    @Override
    public Optional<Inventario> findOne(String id) {
        log.debug("Request to get Inventario : {}", id);
        return inventarioRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Inventario : {}", id);
        inventarioRepository.deleteById(id);
    }
}
