package com.example.api.controller;

import com.example.api.model.UsuarioRequest;
import com.example.api.model.UsuarioResponse;
import com.example.api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST del módulo de usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Lista todos los usuarios.
     *
     * @return 200 con la lista de usuarios
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> getUsuarios() {
        return ResponseEntity.ok(usuarioService.getUsuarios());
    }

    /**
     * Consulta un usuario por id.
     *
     * @param id identificador del usuario
     * @return 200 con el usuario, o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> getUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    /**
     * Crea un usuario.
     *
     * @param request cuerpo JSON con nombre, correo, contrasena y rol
     * @return 201 con el usuario creado, o 400 si el correo ya existe
     */
    @PostMapping
    public ResponseEntity<UsuarioResponse> createUsuario(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUsuario(request));
    }

    /**
     * Actualiza un usuario.
     *
     * @param id      identificador del usuario
     * @param request cuerpo JSON con los nuevos datos
     * @return 200 con el usuario actualizado, o 404 si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> updateUsuario(@PathVariable Long id,
                                                         @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.updateUsuario(id, request));
    }

    /**
     * Elimina un usuario.
     *
     * @param id identificador del usuario
     * @return 204 sin contenido, o 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}