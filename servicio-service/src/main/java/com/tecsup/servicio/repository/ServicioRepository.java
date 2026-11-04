package com.tecsup.servicio.repository;

import com.tecsup.servicio.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    // para no registrar dos servicios con el mismo nombre
    boolean existsByNombreIgnoreCase(String nombre);

    // al actualizar: revisa si otro servicio (con distinto id) ya usa ese nombre
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
