package com.tecsup.recibo.services;

import com.tecsup.recibo.exception.BadRequestException;
import com.tecsup.recibo.exception.DuplicateResourceException;
import com.tecsup.recibo.exception.ResourceNotFoundException;
import com.tecsup.recibo.exception.ServiceUnavailableException;
import com.tecsup.recibo.model.EstadoPago;
import com.tecsup.recibo.model.Recibo;
import com.tecsup.recibo.repository.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ReciboService {

    @Autowired
    private ReciboRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    // las URLs vienen del application.yml (cambian entre dev y prod)
    @Value("${servicios.cliente.url}")
    private String clienteUrl;

    @Value("${servicios.servicio.url}")
    private String servicioUrl;

    public List<Recibo> listar() {
        return repo.findAll();
    }

    public Recibo obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recibo no encontrado con id " + id));
    }

    public List<Recibo> listarPorCliente(Long idCliente) {
        return repo.findByIdCliente(idCliente);
    }

    public List<Recibo> listarPorEstado(EstadoPago estado) {
        return repo.findByEstadoPago(estado);
    }

    public Recibo guardar(Recibo recibo) {
        validarCliente(recibo.getIdCliente());
        validarServicio(recibo.getIdServicio());
        validarFechas(recibo);

        if (repo.existsByIdClienteAndIdServicioAndPeriodo(recibo.getIdCliente(), recibo.getIdServicio(), recibo.getPeriodo())) {
            throw new DuplicateResourceException("El cliente " + recibo.getIdCliente() + " ya tiene un recibo del servicio "
                    + recibo.getIdServicio() + " para el periodo " + recibo.getPeriodo());
        }

        // todo recibo nuevo empieza como PENDIENTE
        recibo.setFechaEmision(LocalDate.now());
        recibo.setEstadoPago(EstadoPago.PENDIENTE);
        recibo.setFechaPago(null);
        return repo.save(recibo);
    }

    public Recibo actualizar(Long id, Recibo nuevo) {
        Recibo existente = obtener(id);

        if (existente.getEstadoPago() == EstadoPago.PAGADO) {
            throw new BadRequestException("El recibo " + id + " ya esta pagado y no se puede modificar");
        }

        validarCliente(nuevo.getIdCliente());
        validarServicio(nuevo.getIdServicio());
        nuevo.setFechaEmision(existente.getFechaEmision());
        validarFechas(nuevo);

        if (repo.existsByIdClienteAndIdServicioAndPeriodoAndIdNot(nuevo.getIdCliente(), nuevo.getIdServicio(), nuevo.getPeriodo(), id)) {
            throw new DuplicateResourceException("Ya existe otro recibo de ese cliente y servicio para el periodo " + nuevo.getPeriodo());
        }

        existente.setIdCliente(nuevo.getIdCliente());
        existente.setIdServicio(nuevo.getIdServicio());
        existente.setPeriodo(nuevo.getPeriodo());
        existente.setMonto(nuevo.getMonto());
        existente.setFechaVencimiento(nuevo.getFechaVencimiento());
        return repo.save(existente);
    }

    // cambia el estado de PENDIENTE a PAGADO y guarda la fecha del pago
    public Recibo pagar(Long id) {
        Recibo recibo = obtener(id);
        if (recibo.getEstadoPago() == EstadoPago.PAGADO) {
            throw new BadRequestException("El recibo " + id + " ya fue pagado el " + recibo.getFechaPago());
        }
        recibo.setEstadoPago(EstadoPago.PAGADO);
        recibo.setFechaPago(LocalDate.now());
        return repo.save(recibo);
    }

    public void eliminar(Long id) {
        obtener(id); // 404 si no existe
        repo.deleteById(id);
    }

    // ---------------- validaciones ----------------

    private void validarFechas(Recibo recibo) {
        LocalDate emision = recibo.getFechaEmision() != null ? recibo.getFechaEmision() : LocalDate.now();
        if (recibo.getFechaVencimiento().isBefore(emision)) {
            throw new BadRequestException("La fecha de vencimiento no puede ser anterior a la fecha de emision (" + emision + ")");
        }
    }

    // consulta a cliente-service si el cliente existe
    private void validarCliente(Long idCliente) {
        try {
            restTemplate.getForObject(clienteUrl + "/api/clientes/" + idCliente, Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("No existe el cliente con id " + idCliente + " en cliente-service");
        } catch (ResourceAccessException e) {
            throw new ServiceUnavailableException("No se pudo conectar con cliente-service, verifique que este levantado");
        }
    }

    // consulta a servicio-service si el servicio existe y esta activo
    private void validarServicio(Long idServicio) {
        Map servicio;
        try {
            servicio = restTemplate.getForObject(servicioUrl + "/api/servicios/" + idServicio, Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("No existe el servicio con id " + idServicio + " en servicio-service");
        } catch (ResourceAccessException e) {
            throw new ServiceUnavailableException("No se pudo conectar con servicio-service, verifique que este levantado");
        }
        if (servicio != null && Boolean.FALSE.equals(servicio.get("activo"))) {
            throw new BadRequestException("El servicio con id " + idServicio + " esta inactivo, no se le pueden generar recibos");
        }
    }
}
