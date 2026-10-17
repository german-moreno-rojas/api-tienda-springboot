package com.example.api.repository;

import com.example.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a datos de la tabla usuarios.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Indica si ya existe un usuario con el correo dado.
     *
     * @param correo correo a buscar
     * @return true si existe
     */
    boolean existsByCorreo(String correo);

    /**
     * Indica si el correo pertenece a otro usuario distinto del indicado.
     *
     * @param correo correo a buscar
     * @param id     id del usuario que se excluye de la búsqueda
     * @return true si otro usuario ya usa ese correo
     */
    boolean existsByCorreoAndIdNot(String correo, Long id);
}