package com.example.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datos de entrada para crear o actualizar un usuario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequest {

    /** Nombre del usuario (obligatorio, máximo 80 caracteres). */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar 80 caracteres")
    private String nombre;

    /** Correo electrónico válido (obligatorio, máximo 120 caracteres). */
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 120, message = "El correo no puede superar 120 caracteres")
    private String correo;

    /** Contraseña (obligatoria, mínimo 6 caracteres). */
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String contrasena;

    /** Rol del usuario (opcional). */
    private String rol;
}