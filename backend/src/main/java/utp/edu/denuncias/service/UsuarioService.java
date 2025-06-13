package utp.edu.denuncias.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.dto.UserUpdateRequest;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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

    /**
     * Elimina un usuario del sistema mediante su identificador.
     *
     * @param id Identificador del usuario a eliminar
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Recupera la lista completa de usuarios registrados en el sistema.
     *
     * @return Lista de objetos {@link Usuario}
     */
    public List<Usuario> listarUsuarios() {
        return userRepository.findAll();
    }

    public Optional<Usuario> updateUser(UserUpdateRequest request) {
        if (request.dni() == null) throw new IllegalArgumentException("DNI no puede ser nulo");
        if (request.nombres() != null) userRepository.updateNombreByDni(request.dni(), request.nombres());
        if (request.apellidos() != null) userRepository.updateApellidosByDni(request.dni(), request.apellidos());
        if (request.password() != null) userRepository.updatePasswordByDni(request.dni(), request.password());
        return userRepository.findByDni(request.dni());
    }
}