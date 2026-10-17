package com.example.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad que representa a un usuario de la tienda.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {

    /** Identificador autogenerado. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre completo del usuario. */
    @Column(nullable = false, length = 80)
    private String nombre;

    /** Correo electrónico, único en toda la tabla. */
    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    /** Contraseña del usuario. Nunca se devuelve en las respuestas. */
    @Column(nullable = false)
    private String contrasena;

    /** Rol del usuario (por ejemplo ADMIN o CLIENTE). */
    private String rol;
}