package com.tecsup.servicio.controller;

import com.tecsup.servicio.dto.ServicioDTO;
import com.tecsup.servicio.model.Servicio;
import com.tecsup.servicio.services.ServicioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioService service;

    @GetMapping
    public List<Servicio> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Servicio obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    public ResponseEntity<Servicio> guardar(@Valid @RequestBody ServicioDTO dto) {
        Servicio servicio = convertir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(servicio));
    }

    @PutMapping("/{id}")
    public Servicio actualizar(@PathVariable Long id, @Valid @RequestBody ServicioDTO dto) {
        return service.actualizar(id, convertir(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // pasa los datos del DTO a la entidad
    private Servicio convertir(ServicioDTO dto) {
        return new Servicio(null, dto.getNombre(), dto.getDescripcion(), dto.getEmpresa(),
                dto.getTarifaMensual(), dto.getActivo());
    }
}
