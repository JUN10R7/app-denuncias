package utp.edu.denuncias.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utp.edu.denuncias.dto.UserUpdateRequest;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.service.UsuarioService;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios desde el rol de administrador.
 */
@RestController
@RequiredArgsConstructor
public class UsuarioController {

    /** Servicio que contiene la lógica de negocio para usuarios */
    private final UsuarioService usuarioService;

    /**
     * Obtiene la lista completa de usuarios registrados.
     * Acceso reservado para administradores.
     *
     * @return lista de objetos {@link Usuario}
     */
    @GetMapping("/admin/usuario")
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    /**
     * Elimina un usuario mediante su ID.
     *
     * @param id identificador del usuario a eliminar
     */
    @DeleteMapping("/admin/usuario")
    public void eliminarUsuario(@RequestParam("id") Long id) {
        usuarioService.deleteUser(id);
    }

    @PutMapping("/usuario")
    public ResponseEntity<?> updateUser(UserUpdateRequest request) {
        return ResponseEntity.ok(usuarioService.updateUser(request));
    }
}