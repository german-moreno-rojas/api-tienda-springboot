package com.example.api.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Se lanza cuando no existe un usuario con el id solicitado.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends RuntimeException {

    /**
     * @param message detalle del error
     */
    public UsuarioNotFoundException(String message) {
        super(message);
    }
}