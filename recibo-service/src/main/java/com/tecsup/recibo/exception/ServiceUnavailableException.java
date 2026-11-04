package com.tecsup.recibo.exception;

// Se lanza cuando cliente-service o servicio-service no responden; el handler la convierte en 503
public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String mensaje) {
        super(mensaje);
    }
}
