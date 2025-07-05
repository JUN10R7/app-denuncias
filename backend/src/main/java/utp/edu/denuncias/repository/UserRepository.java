package utp.edu.denuncias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.edu.denuncias.enums.Rol;
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
    Optional<Usuario> findByUsernameAndEnabledTrue(String username);

    /**
     * Devuelve una lista de usuarios que tienen el atributo "enabled" establecido en true.
     *
     * @return Lista de usuarios habilitados en el sistema
     */
    List<Usuario> findByEnabledTrue();

    /**
     * Devuelve una lista de usuarios que tienen el rol especificado.
     *
     * @param rol El rol por el cual se filtrarán los usuarios
     * @return Lista de usuarios que poseen el rol especificado
     */
    List<Usuario> findAllByRol(Rol rol);
}
