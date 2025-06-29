package utp.edu.denuncias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.edu.denuncias.model.Usuario;
import java.util.*;

/**
 * Repositorio JPA para la entidad Usuario.
 * Proporciona métodos para realizar operaciones CRUD y consultas específicas sobre usuarios.
 */
public interface UserRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su nombre de usuario (username).
     *
     * @param username Nombre de usuario
     * @return Optional con el usuario encontrado o vacío si no existe
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Devuelve una lista de usuarios que tienen el atributo "enabled" establecido en true.
     *
     * @return Lista de usuarios habilitados en el sistema
     */
    List<Usuario> findByEnabledTrue();
}
