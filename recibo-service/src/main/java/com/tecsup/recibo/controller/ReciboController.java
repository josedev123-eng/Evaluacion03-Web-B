package com.tecsup.recibo.controller;

import com.tecsup.recibo.dto.ReciboDTO;
import com.tecsup.recibo.model.EstadoPago;
import com.tecsup.recibo.model.Recibo;
import com.tecsup.recibo.services.ReciboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recibos")
public class ReciboController {

    @Autowired
    private ReciboService service;

    @GetMapping
    public List<Recibo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Recibo obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<Recibo> listarPorCliente(@PathVariable Long idCliente) {
        return service.listarPorCliente(idCliente);
    }

    @GetMapping("/estado/{estado}")
    public List<Recibo> listarPorEstado(@PathVariable EstadoPago estado) {
        return service.listarPorEstado(estado);
    }

    @PostMapping
    public ResponseEntity<Recibo> guardar(@Valid @RequestBody ReciboDTO dto) {
        Recibo recibo = convertir(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(recibo));
    }

    @PutMapping("/{id}")
    public Recibo actualizar(@PathVariable Long id, @Valid @RequestBody ReciboDTO dto) {
        return service.actualizar(id, convertir(dto));
    }

    // marca el recibo como PAGADO
    @PatchMapping("/{id}/pagar")
    public Recibo pagar(@PathVariable Long id) {
        return service.pagar(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // pasa los datos del DTO a la entidad
    private Recibo convertir(ReciboDTO dto) {
        return new Recibo(null, dto.getIdCliente(), dto.getIdServicio(), dto.getPeriodo(),
                dto.getMonto(), dto.getFechaVencimiento());
    }
}
