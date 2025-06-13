package utp.edu.denuncias.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.model.Token;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Token.
 * Proporciona métodos para acceder y modificar tokens de usuario en la base de datos.
 */
public interface TokenRepository extends JpaRepository<Token, Long> {

    /**
     * Busca un token por su valor único.
     *
     * @param token Valor del token
     * @return Optional que contiene el token si existe, o vacío en caso contrario
     */
    Optional<Token> findByToken(String token);

    /**
     * Obtiene todos los tokens asociados a un usuario que no estén ni expirados ni revocados.
     *
     * @param usuario Usuario al que pertenecen los tokens
     * @return Lista de tokens activos (no expirados ni revocados) para el usuario dado
     */
    List<Token> findAllByUsuarioAndExpiredFalseAndRevokedFalse(Usuario usuario);

    /**
     * Marca un token como revocado en la base de datos.
     *
     * @param token Valor del token que se desea revocar
     * @return Número de filas afectadas por la actualización (normalmente 1 si el token existe)
     *
     * Anotaciones:
     * - @Transactional: Asegura que la operación se ejecute dentro de una transacción.
     * - @Modifying: Indica que esta consulta es de modificación (update/delete).
     * - @Query: Consulta JPQL personalizada para actualizar el campo 'revoked' a true donde coincida el token.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Token t SET t.revoked = true WHERE t.token = ?1")
    int revokeToken(String token);
}