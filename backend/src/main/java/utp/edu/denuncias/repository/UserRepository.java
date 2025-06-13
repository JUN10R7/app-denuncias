package utp.edu.denuncias.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
     * Busca un usuario por su DNI.
     *
     * @param dni Documento Nacional de Identidad
     * @return Optional con el usuario encontrado o vacío si no existe
     */
    Optional<Usuario> findByDni(String dni);

    @Transactional
    @Modifying
    @Query("DELETE FROM Usuario u WHERE u.dni = ?1")
    void deleteByDni(String dni);


    @Transactional
    @Modifying
    @Query("UPDATE Usuario u SET u.nombres = ?1 WHERE u.dni = ?2")
    int updateNombreByDni(String nombre, String dni);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario u SET u.apellidos = ?1 WHERE u.dni = ?2")
    int updateApellidosByDni(String apellidos, String dni);

    @Transactional
    @Modifying
    @Query("UPDATE Usuario u SET u.password = ?1 WHERE u.dni = ?2")
    int updatePasswordByDni(String password, String dni);
}
