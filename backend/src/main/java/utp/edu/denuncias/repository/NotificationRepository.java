package utp.edu.denuncias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.edu.denuncias.model.Notification;

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
}
