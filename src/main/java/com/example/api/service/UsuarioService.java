package com.example.api.service;

import com.example.api.model.Usuario;
import com.example.api.model.UsuarioRequest;
import com.example.api.model.UsuarioResponse;
import com.example.api.repository.UsuarioRepository;
import com.example.api.utils.UsuarioNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lógica de negocio del módulo de usuarios.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Lista todos los usuarios.
     *
     * @return lista de usuarios sin contraseña
     */
    public List<UsuarioResponse> getUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponse::from)
                .toList();
    }

    /**
     * Consulta un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return el usuario encontrado, sin contraseña
     * @throws UsuarioNotFoundException si el id no existe
     */
    public UsuarioResponse getUsuarioById(Long id) {
        return UsuarioResponse.from(findOrThrow(id));
    }

    /**
     * Crea un usuario, verificando que el correo no esté en uso.
     *
     * @param request datos del usuario a crear
     * @return el usuario creado, sin contraseña
     * @throws IllegalArgumentException si el correo ya existe
     */
    public UsuarioResponse createUsuario(UsuarioRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("Ya existe un usuario con el correo " + request.getCorreo());
        }
        log.info("Creando usuario: {}", request.getCorreo());
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .contrasena(request.getContrasena())
                .rol(request.getRol())
                .build();
        return UsuarioResponse.from(usuarioRepository.save(usuario));
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id      identificador del usuario
     * @param request nuevos datos
     * @return el usuario actualizado, sin contraseña
     * @throws UsuarioNotFoundException si el id no existe
     * @throws IllegalArgumentException si el correo pertenece a otro usuario
     */
    public UsuarioResponse updateUsuario(Long id, UsuarioRequest request) {
        Usuario existing = findOrThrow(id);
        if (usuarioRepository.existsByCorreoAndIdNot(request.getCorreo(), id)) {
            throw new IllegalArgumentException("Ya existe un usuario con el correo " + request.getCorreo());
        }
        existing.setNombre(request.getNombre());
        existing.setCorreo(request.getCorreo());
        existing.setContrasena(request.getContrasena());
        existing.setRol(request.getRol());
        return UsuarioResponse.from(usuarioRepository.save(existing));
    }

    /**
     * Elimina un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @throws UsuarioNotFoundException si el id no existe
     */
    public void deleteUsuario(Long id) {
        usuarioRepository.delete(findOrThrow(id));
    }

    private Usuario findOrThrow(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("No encontramos ese ID"));
    }
}