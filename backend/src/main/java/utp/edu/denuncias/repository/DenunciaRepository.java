package utp.edu.denuncias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.edu.denuncias.model.Denuncia;

import java.util.List;

/**
 * Repositorio JPA para la entidad Denuncia.
 * Proporciona métodos para realizar operaciones CRUD sobre denuncias en la base de datos.
 */
public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {

    /**
     * Busca y devuelve todas las denuncias asociadas a un usuario específico.
     *
     * @param userId Identificador único del usuario
     * @return Lista de denuncias creadas por el usuario con el id dado
     */
    List<Denuncia> findByUsuarioId(Long userId);
}