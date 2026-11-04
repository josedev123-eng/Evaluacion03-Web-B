package com.tecsup.recibo.exception;

// Se lanza cuando se intenta registrar un dato que ya existe; el handler la convierte en 409
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String mensaje) {
        super(mensaje);
    }
}
