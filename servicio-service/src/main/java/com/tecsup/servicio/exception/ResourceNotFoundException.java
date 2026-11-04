package com.tecsup.servicio.exception;

// Se lanza cuando no existe el recurso buscado; el handler la convierte en 404
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
