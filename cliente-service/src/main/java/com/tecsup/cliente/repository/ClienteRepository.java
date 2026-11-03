package com.tecsup.cliente.repository;

import com.tecsup.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // para no registrar dos clientes con el mismo DNI
    boolean existsByDni(String dni);

    // al actualizar: revisa si otro cliente (con distinto id) ya tiene ese DNI
    boolean existsByDniAndIdNot(String dni, Long id);
}
