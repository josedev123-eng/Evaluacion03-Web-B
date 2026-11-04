package com.tecsup.servicio.services;

import com.tecsup.servicio.exception.DuplicateResourceException;
import com.tecsup.servicio.exception.ResourceNotFoundException;
import com.tecsup.servicio.model.Servicio;
import com.tecsup.servicio.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository repo;

    public List<Servicio> listar() {
        return repo.findAll();
    }

    public Servicio obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Servicio no encontrado con id " + id));
    }

    public Servicio guardar(Servicio servicio) {
        if (repo.existsByNombreIgnoreCase(servicio.getNombre())) {
            throw new DuplicateResourceException("Ya existe un servicio con el nombre " + servicio.getNombre());
        }
        if (servicio.getActivo() == null) {
            servicio.setActivo(true);
        }
        return repo.save(servicio);
    }

    public Servicio actualizar(Long id, Servicio nuevo) {
        Servicio existente = obtener(id);
        if (repo.existsByNombreIgnoreCaseAndIdNot(nuevo.getNombre(), id)) {
            throw new DuplicateResourceException("Ya existe otro servicio con el nombre " + nuevo.getNombre());
        }
        existente.setNombre(nuevo.getNombre());
        existente.setDescripcion(nuevo.getDescripcion());
        existente.setEmpresa(nuevo.getEmpresa());
        existente.setTarifaMensual(nuevo.getTarifaMensual());
        if (nuevo.getActivo() != null) {
            existente.setActivo(nuevo.getActivo());
        }
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        obtener(id); // 404 si no existe
        repo.deleteById(id);
    }
}
