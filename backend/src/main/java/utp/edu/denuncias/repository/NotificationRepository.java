package utp.edu.denuncias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.edu.denuncias.model.Denuncia;
import utp.edu.denuncias.model.Notification;
import utp.edu.denuncias.model.Solicitud;

import java.util.List;

/**
 * Repositorio JPA para la entidad Notification.
 * Proporciona métodos para realizar operaciones CRUD y consultas específicas sobre notificaciones.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    /**
     * Recupera una lista de notificaciones filtradas por el identificador del destinatario,
     * ordenadas en orden descendente por la fecha de creación.
     *
     * @param userId Identificador único del destinatario para el que se buscan las notificaciones.
     * @return Lista de notificaciones pertenecientes al destinatario especificado,
     * ordenadas por fecha de creación en orden descendente.
     */
    List<Notification> findAllByDestinatarioIdOrderByCreatedDateDesc(Long userId);

    /**
     * Recupera una lista de notificaciones asociadas a una solicitud específica
     * que aún no han sido marcadas como leídas.
     *
     * @param solicitud La solicitud a la que están asociadas las notificaciones buscadas.
     * @return Lista de notificaciones relacionadas con la solicitud proporcionada
     *         y que no han sido leídas.
     */
    List<Notification> findBySolicitudAndReadFalse(Solicitud solicitud);

    /**
     * Recupera una lista de notificaciones asociadas a una denuncia específica y cuyo estado no indica que han sido leídas.
     *
     * @param denuncia La denuncia a la que están asociadas las notificaciones buscadas.
     * @return Lista de notificaciones relacionadas con la denuncia especificada y que no han sido marcadas como leídas.
     */
    List<Notification> findByDenunciaAndReadFalse(Denuncia denuncia);
}
