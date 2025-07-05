package utp.edu.denuncias.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.dto.NotificationResponse;
import utp.edu.denuncias.dto.UsuarioResponse;
import utp.edu.denuncias.dto.UsuarioUpdateRequest;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.repository.UserRepository;
import utp.edu.denuncias.security.JwtUtil;

import java.util.List;

/**
 * Servicio que gestiona las operaciones relacionadas con los usuarios del sistema.
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {

    /**
     * Repositorio para realizar operaciones CRUD sobre los usuarios
     */
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;

    /**
     * Obtiene una lista de usuarios en el sistema y los representa como objetos {@code UsuarioResponse}.
     *
     * @return Una lista de objetos {@code UsuarioResponse} que contiene la información de todos los usuarios registrados.
     */
    public List<UsuarioResponse> listarUsuarios() {
        return UsuarioResponse.from(userRepository.findAll());
    }

    /**
     * Obtiene una lista de usuarios activos en el sistema y los representa como objetos {@code UsuarioResponse}.
     *
     * @return Una lista de objetos {@code UsuarioResponse} que contiene la información de los usuarios activos registrados.
     */
    public List<UsuarioResponse> listarUsuariosActivos() {
        return UsuarioResponse.from(userRepository.findByEnabledTrue());
    }

    /**
     * Recupera un usuario a partir de su identificador único.
     *
     * @param id Identificador único del usuario que se desea consultar.
     * @return Un objeto {@link UsuarioResponse} que contiene la información del usuario recuperado.
     * @throws RuntimeException Si el usuario con el identificador proporcionado no existe.
     */
    public UsuarioResponse findById(Long id) {
        Usuario usuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario con ID" + id + " no encontrado"));
        return UsuarioResponse.from(usuario);
    }

    public UsuarioResponse findByCurrentUser() {
        String username = JwtUtil.getCurrentUsername();
        Usuario usuario = userRepository.findByUsernameAndEnabledTrue(username).orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario autenticado"));
        return UsuarioResponse.from(usuario);
    }

    /**
     * Actualiza la información de un usuario en el sistema.
     * Este método permite modificar el nombre de usuario, contraseña, correo electrónico y el rol
     * de un usuario existente, si se proporcionan nuevos valores en la solicitud.
     *
     * @param request Objeto {@link UsuarioUpdateRequest} que contiene los nuevos datos para actualizar el usuario.
     *                Los valores nulos en la solicitud no actualizarán las propiedades correspondientes.
     * @return Un objeto {@link UsuarioResponse} que representa el usuario actualizado, incluyendo su información
     * básica excepto la contraseña, la cual es manipulada de forma segura.
     * @throws RuntimeException Si el usuario no es encontrado en el sistema.
     */
    @Transactional
    public UsuarioResponse updateUser(UsuarioUpdateRequest request) {
        String username = JwtUtil.getCurrentUsername();

        if (username == null || username.equals("anonymousUser")) {
            throw new RuntimeException("No se pudo obtener el usuario autenticado");
        }

        Usuario usuario = userRepository.findByUsernameAndEnabledTrue(username).orElseThrow(() ->
                new RuntimeException("Usuario con " + username + ", no encontrado"));

        if (request.username() != null) {
            usuario.setUsername(request.username());
        }
        if (request.password() != null) {
            usuario.setPassword(passwordEncoder.encode(request.password()));
        }
        if (request.email() != null) {
            usuario.setEmail(request.email());
        }
        if (request.rol() != null) {
            usuario.setRol(request.rol());
        }

        return UsuarioResponse.from(userRepository.save(usuario));
    }

    /**
     * Activa un usuario en el sistema, marcándolo como habilitado.
     * El ID proporcionado se utiliza para identificar al usuario que debe ser activado.
     *
     * @param id Identificador único del usuario que se desea activar.
     * @throws RuntimeException Si no se encuentra un usuario con el ID proporcionado.
     */
    @Transactional
    public void activarUsuario(Long id) {
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID" + id + " no encontrado"));
        usuario.setEnabled(true);
        userRepository.save(usuario);
    }

    /**
     * Desactiva un usuario en el sistema, marcándolo como no habilitado.
     * El ID proporcionado se utiliza para identificar al usuario que debe ser desactivado.
     *
     * @param id Identificador único del usuario que se desea desactivar.
     * @throws RuntimeException Si no se encuentra un usuario con el ID proporcionado.
     */
    @Transactional
    public void desactivarUsuario(Long id) {
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID" + id + " no encontrado"));
        usuario.setEnabled(false);
        userRepository.save(usuario);
    }

    /**
     * Desactiva al usuario actualmente autenticado en el sistema, marcándolo como no habilitado.
     * El usuario autenticado es identificado a partir del token proporcionado.
     *
     * @throws RuntimeException Si no se puede obtener al usuario autenticado o el usuario no se encuentra en el sistema.
     */
    @Transactional
    public void desactivarUsuario() {
        String username = JwtUtil.getCurrentUsername();
        Usuario usuario = userRepository.findByUsernameAndEnabledTrue(username)
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario autenticado"));
        usuario.setEnabled(false);
        userRepository.save(usuario);
    }

    /**
     * Obtiene la lista de notificaciones del usuario actualmente autenticado.
     *
     * @return Una lista de objetos {@code NotificationResponse} que representan las notificaciones asociadas
     * al usuario autenticado.
     * @throws RuntimeException Si no se puede obtener el usuario autenticado.
     */
    public List<NotificationResponse> obtenerNotificaciones() {
        var user = userRepository.findByUsernameAndEnabledTrue(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario autenticado"));
        return notificationService.obtenerNotificaciones(user.getId());
    }

    /**
     * Marca una notificación específica como leída para el usuario actualmente autenticado.
     *
     * @param id Identificador único de la notificación que se desea marcar como leída.
     * @throws RuntimeException Si no se puede obtener el usuario autenticado.
     */
    public void marcarNotificationLeida(Long id) {
        var user = userRepository.findByUsernameAndEnabledTrue(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario autenticado"));
        notificationService.marcarComoLeida(id, user);
    }
}