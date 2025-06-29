package utp.edu.denuncias.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utp.edu.denuncias.dto.UsuarioResponse;
import utp.edu.denuncias.dto.UsuarioUpdateRequest;
import utp.edu.denuncias.dto.UsuarioUpdateResponse;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.service.UsuarioService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    /**
     * Recupera una lista de todos los usuarios registrados en el sistema en forma de objetos {@code UsuarioResponse}.
     *
     * @return un {@link ResponseEntity} que contiene una lista de objetos {@link UsuarioResponse},
     *         cada uno representando la información de un usuario registrado.
     */
    @GetMapping("/mod/usuario")
    public ResponseEntity<List<UsuarioResponse>> listarUsuariosActivos() {
        return ResponseEntity.ok(usuarioService.listarUsuariosActivos());
    }

    /**
     * Recupera una lista de todos los usuarios registrados en el sistema.
     *
     * @return un {@link ResponseEntity} que contiene una lista de objetos {@link UsuarioResponse},
     *         cada uno representando la información de un usuario registrado.
     */
    @GetMapping("/admin/usuario")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    /**
     * Busca y devuelve los detalles de un usuario específico utilizando su identificador único.
     *
     * @param id identificador único del usuario que se desea buscar
     * @return un {@link ResponseEntity} que contiene el objeto {@link Usuario} correspondiente al identificador proporcionado
     */
    @GetMapping("/mod/usuario/{id}")
    public ResponseEntity<UsuarioResponse> listarUsuario(@PathVariable Long id) {
        System.out.println("Buscando usuario con id: " + id);
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    /**
     * Obtiene los datos del usuario actualmente autenticado.
     *
     * @return un {@link ResponseEntity} que contiene un {@link UsuarioResponse}
     *         con la información del usuario autenticado
     */
    @GetMapping("/usuario")
    public ResponseEntity<UsuarioResponse> getUsuario() {
        return ResponseEntity.ok(usuarioService.findByCurrentUser());
    }

    /**
     * Actualiza la información del usuario autenticado en el sistema.
     * Este método recibe un objeto de solicitud con los datos que deben actualizarse
     * y devuelve una respuesta que contiene la información del usuario actualizado.
     *
     * @param request objeto {@link UsuarioUpdateRequest} que contiene los nuevos valores para modificar
     *                los datos del usuario. Los campos nulos en la solicitud no modificarán los valores actuales.
     * @return un {@link ResponseEntity} que incluye un objeto {@link UsuarioUpdateResponse} con la información
     *         del usuario actualizado, excluyendo la contraseña.
     */
    @PutMapping("/usuario")
    public ResponseEntity<UsuarioUpdateResponse> updateUser(@RequestBody UsuarioUpdateRequest request) {
        UsuarioUpdateResponse response = usuarioService.updateUser(request);
        System.out.println(response);
        return ResponseEntity.ok(response);
    }

    /**
     * Activa a un usuario específico en el sistema, marcándolo como habilitado.
     * El usuario es identificado a través de su ID único.
     *
     * @param id Identificador único del usuario que se desea activar.
     */
    @PutMapping("/admin/usuario/{id}")
    public void updateUser(@PathVariable Long id) {
        usuarioService.activarUsuario(id);
    }

    /**
     * Elimina un usuario registrado en el sistema basándose en su identificador único proporcionado.
     *
     * @param id identificador único del usuario que se desea eliminar
     */
    @DeleteMapping("/admin/usuario/{id}")
    public void desactivarUsuario(@PathVariable Long id) {
        usuarioService.desactivarUsuario(id);
    }

    /**
     * Desactiva al usuario actualmente autenticado en el sistema, marcándolo como no habilitado.
     * Este método no requiere parámetros ya que identifica al usuario a través del token
     * de autenticación proporcionado en la solicitud.
     *
     * @throws RuntimeException si no se puede obtener al usuario autenticado o el usuario no se encuentra
     *                          en el sistema.
     */
    @DeleteMapping("/usuario")
    public void desactivarUsuario() {
        usuarioService.desactivarUsuario();
    }
}