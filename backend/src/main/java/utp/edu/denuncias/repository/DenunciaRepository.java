package utp.edu.denuncias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.edu.denuncias.enums.Estado;
import utp.edu.denuncias.model.Denuncia;

import java.util.List;
import java.util.Optional;

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

    /**
     * Busca y devuelve una denuncia específica asociada a un usuario dado.
     *
     * @param userId Identificador único del usuario propietario de la denuncia
     * @param id Identificador único de la denuncia que se desea buscar
     * @return Un objeto Optional que contiene la denuncia encontrada, o vacío si no existe
     */
    Optional<Denuncia> findByUsuarioIdAndId(Long userId, Long id);

    /**
     * Busca y devuelve todas las denuncias que no tienen un moderador asignado y cuyo estado no coincide
     * con el estado proporcionado.
     *
     * @param estados Estado que será excluido de la búsqueda
     * @return Lista de denuncias que no tienen un moderador asignado y cuyo estado no es el especificado
     */
    List<Denuncia> findAllByModAsignadoIsNullAndEstadoNotIn(List<Estado> estados);

    /**
     * Busca y devuelve todas las denuncias asignadas a un moderador específico cuyo estado
     * no coincide con el estado proporcionado.
     *
     * @param modId Identificador único del moderador al que están asignadas las denuncias
     * @param estados Estado que será excluido de la búsqueda
     * @return Lista de denuncias asignadas al moderador cuyo estado no coincide con el proporcionado
     */
    List<Denuncia> findAllByModAsignadoIdAndEstadoNotIn(Long modId, List<Estado> estados);

    /**
     * Busca y devuelve todas las denuncias asignadas a un moderador específico.
     *
     * @param modId Identificador único del moderador al que están asignadas las denuncias
     * @return Lista de denuncias asignadas al moderador correspondiente
     */
    List<Denuncia> findAllByModAsignadoId(Long modId);
}