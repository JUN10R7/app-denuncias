package utp.edu.denuncias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import utp.edu.denuncias.enums.Estado;
import utp.edu.denuncias.model.Solicitud;

import java.util.List;

/**
 * Repositorio JPA para la entidad Solicitud.
 * Proporciona métodos para realizar operaciones CRUD básicas sobre solicitudes.
 * Esta interfaz permite interactuar con la base de datos
 * y realizar consultas relacionadas con las solicitudes registradas en el sistema.
 */
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    /**
     * Busca y devuelve una lista de solicitudes donde el usuario proporcionado
     * es autor o revisor, ordenadas en orden descendente por la fecha de creación.
     *
     * @param usuarioId el identificador único del usuario que puede ser autor o revisor
     *                  de las solicitudes.
     * @return una lista de objetos Solicitud en los que el usuario especificado
     *         es autor o revisor, ordenadas por fecha de creación en orden descendente.
     */
    @Query("SELECT s FROM Solicitud s WHERE (s.autor.id = :usuarioId OR s.revisor.id = :usuarioId) ORDER BY s.createdDate desc")
    List<Solicitud> findByAutorOrRevisorId(@Param("usuarioId") Long usuarioId);

    /**
     * Busca y devuelve una lista de solicitudes cuyo revisor no está asignado
     * (es nulo), ordenadas en orden descendente por la fecha de creación.
     *
     * @return una lista de objetos Solicitud no asignadas a ningún revisor,
     *         ordenadas por fecha de creación en orden descendente.
     */
    List<Solicitud> findByRevisorIsNullOrderByCreatedDateDesc();

    /**
     * Busca y devuelve una lista de solicitudes asociadas a una denuncia específica,
     * ordenadas en orden descendente por la fecha de creación.
     *
     * @param denunciaId el identificador único de la denuncia asociada a las solicitudes.
     * @return una lista de objetos Solicitud ordenados en orden descendente
     *         según la fecha de creación.
     */
    List<Solicitud> findByDenunciaIdOrderByCreatedDateDesc(Long denunciaId);

}