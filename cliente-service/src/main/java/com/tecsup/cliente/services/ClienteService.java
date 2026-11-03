package com.tecsup.cliente.services;

import com.tecsup.cliente.exception.DuplicateResourceException;
import com.tecsup.cliente.exception.ResourceNotFoundException;
import com.tecsup.cliente.model.Cliente;
import com.tecsup.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public List<Cliente> listar() {
        return repo.findAll();
    }

    public Cliente obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id " + id));
    }

    public Cliente guardar(Cliente cliente) {
        if (repo.existsByDni(cliente.getDni())) {
            throw new DuplicateResourceException("Ya existe un cliente con el DNI " + cliente.getDni());
        }
        return repo.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente nuevo) {
        Cliente existente = obtener(id);
        if (repo.existsByDniAndIdNot(nuevo.getDni(), id)) {
            throw new DuplicateResourceException("Ya existe otro cliente con el DNI " + nuevo.getDni());
        }
        existente.setDni(nuevo.getDni());
        existente.setNombres(nuevo.getNombres());
        existente.setApellidos(nuevo.getApellidos());
        existente.setCorreo(nuevo.getCorreo());
        existente.setTelefono(nuevo.getTelefono());
        existente.setDireccion(nuevo.getDireccion());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        obtener(id); // 404 si no existe
        repo.deleteById(id);
    }
}
