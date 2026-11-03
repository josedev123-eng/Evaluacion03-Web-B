package com.tecsup.cliente.controller;

import com.tecsup.cliente.dto.ClienteDTO;
import com.tecsup.cliente.model.Cliente;
import com.tecsup.cliente.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public List<Cliente> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Cliente obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    public ResponseEntity<Cliente> guardar(@Valid @RequestBody ClienteDTO dto) {
        Cliente cliente = convertir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cliente));
    }

    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return service.actualizar(id, convertir(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // pasa los datos del DTO a la entidad
    private Cliente convertir(ClienteDTO dto) {
        return new Cliente(null, dto.getDni(), dto.getNombres(), dto.getApellidos(),
                dto.getCorreo(), dto.getTelefono(), dto.getDireccion());
    }
}
