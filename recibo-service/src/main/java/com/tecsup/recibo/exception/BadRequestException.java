package com.tecsup.recibo.exception;

// Se lanza cuando no se cumple una regla del negocio; el handler la convierte en 400
public class BadRequestException extends RuntimeException {

    public BadRequestException(String mensaje) {
        super(mensaje);
    }
}
