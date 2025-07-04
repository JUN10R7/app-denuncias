package utp.edu.denuncias.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.dto.NotificationResponse;
import utp.edu.denuncias.model.Denuncia;
import utp.edu.denuncias.model.Notification;
import utp.edu.denuncias.model.Solicitud;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.repository.NotificationRepository;

import java.util.List;

/**
 * Servicio encargado de gestionar las notificaciones en el sistema.
 * Proporciona métodos para crear y recuperar notificaciones relacionadas
 * con usuarios, denuncias y solicitudes.
 */
@Service
@RequiredArgsConstructor
public class NotificationService {
    /**
     * Repositorio utilizado para interactuar con la base de datos y gestionar
     * las operaciones relacionadas con la entidad Notification.
     * Proporciona acceso a métodos CRUD y consultas específicas.
     */
    private final NotificationRepository notificationRepository;

    /**
     * Notifica a un usuario enviando una notificación con un título y mensaje,
     * asociada opcionalmente a una denuncia y/o solicitud específica.
     *
     * @param destinatario Usuario que recibirá la notificación.
     * @param titulo Título del mensaje de la notificación.
     * @param mensaje Contenido del mensaje de la notificación.
     * @param denuncia Denuncia asociada a la notificación, puede ser null si no aplica.
     * @param solicitud Solicitud asociada a la notificación, puede ser null si no aplica.
     */
    public void notificar(Usuario destinatario, String titulo, String mensaje, Denuncia denuncia, Solicitud solicitud) {
        Notification notification = Notification.builder()
                .titulo(titulo)
                .message(mensaje)
                .denuncia(denuncia)
                .solicitud(solicitud)
                .destinatario(destinatario)
                .build();
        notificationRepository.save(notification);
    }

    /**
     * Marca una notificación específica como leída para un usuario dado.
     * Si el usuario no es el destinatario de la notificación, se lanza una excepción de acceso denegado.
     *
     * @param idNotificacion el identificador único de la notificación a marcar como leída
     * @param usuario el usuario que realiza la acción de marcar la notificación como leída
     * @throws RuntimeException si no se encuentra la notificación con el ID proporcionado
     * @throws AccessDeniedException si el usuario no es el destinatario de la notificación
     */
    @Transactional
    public void marcarComoLeida(Long idNotificacion, Usuario usuario) {
        var notification = notificationRepository.findById(idNotificacion)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        if (!notification.getDestinatario().getId().equals(usuario.getId())) {
            throw new AccessDeniedException("No puedes marcar como leída esta notificación");
        }
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    /**
     * Recupera una lista de notificaciones asociadas a un destinatario específico, ordenadas
     * por fecha de creación de manera descendente.
     *
     * @param id el identificador único del destinatario cuyas notificaciones se desean recuperar
     * @return una lista de objetos {@link NotificationResponse} que representan las notificaciones ordenadas
     */
    public List<NotificationResponse> obtenerNotificaciones(Long id) {
        return NotificationResponse.from(notificationRepository.findAllByDestinatarioIdOrderByCreatedDateDesc(id));
    }

    public void marcarNotificacionesRelacionadasComoVistas(Solicitud solicitud) {
        var relacionadas = notificationRepository.findBySolicitudAndReadFalse(solicitud);
        relacionadas.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(relacionadas);
    }

}
