package com.example.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de un usuario que se devuelven al cliente. No incluye la contraseña.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String correo;
    private String rol;

    /**
     * Convierte una entidad en su respuesta, omitiendo la contraseña.
     *
     * @param u entidad de usuario
     * @return objeto de respuesta sin datos sensibles
     */
    public static UsuarioResponse from(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombre(), u.getCorreo(), u.getRol());
    }
}