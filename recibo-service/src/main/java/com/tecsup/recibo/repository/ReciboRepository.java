package com.tecsup.recibo.repository;

import com.tecsup.recibo.model.EstadoPago;
import com.tecsup.recibo.model.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReciboRepository extends JpaRepository<Recibo, Long> {

    List<Recibo> findByIdCliente(Long idCliente);

    List<Recibo> findByEstadoPago(EstadoPago estadoPago);

    // un cliente no puede tener dos recibos del mismo servicio en el mismo periodo
    boolean existsByIdClienteAndIdServicioAndPeriodo(Long idCliente, Long idServicio, String periodo);

    boolean existsByIdClienteAndIdServicioAndPeriodoAndIdNot(Long idCliente, Long idServicio, String periodo, Long id);
}
