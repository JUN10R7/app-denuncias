package utp.edu.denuncias.dto;

import lombok.Builder;
import utp.edu.denuncias.model.Notification;

import java.util.List;

/**
 * Registro (record) que representa la respuesta de notificación utilizada en el sistema.
 * Proporciona información relevante asociada a una notificación específica.
 * Este DTO encapsula los datos de una notificación, incluyendo su identificador, título,
 * mensaje, estado de lectura, fecha de creación, y títulos asociados a posibles denuncias
 * o solicitudes relacionadas.
 */
@Builder
public record NotificationResponse(
        Long id,
        String titulo,
        String message,
        boolean read,
        String fecha,
        String denunciaTitulo,
        Long idDenuncia,
        String solicitudTitulo,
        Long idSolicitud
) {
    /**
     * Crea una instancia de NotificationResponse a partir de un objeto Notification.
     *
     * @param notification la notificación fuente a partir de la cual se construye el objeto NotificationResponse
     * @return una instancia de NotificationResponse que contiene los datos mapeados de la notificación proporcionada
     */
    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .titulo(notification.getTitulo())
                .message(notification.getMessage())
                .read(notification.isVisto())
                .fecha(notification.getCreatedDate().toString())
                .denunciaTitulo(notification.getDenuncia() != null ? notification.getDenuncia().getTitulo() : null)
                .idDenuncia(notification.getDenuncia() != null ? notification.getDenuncia().getId() : null)
                .solicitudTitulo(notification.getSolicitud() != null ? notification.getSolicitud().getTitulo() : null)
                .idSolicitud(notification.getSolicitud() != null ? notification.getSolicitud().getId() : null)
                .build();
    }
    /**
     * Convierte una lista de objetos Notification en una lista de objetos NotificationResponse.
     * Aplica el método de conversión estático en cada elemento de la lista proporcionada.
     *
     * @param notifications lista de notificaciones a convertir
     * @return lista de objetos NotificationResponse generados a partir de las notificaciones de entrada
     */
    public static List<NotificationResponse> from(List<Notification> notifications) {
        return notifications.stream()
                .map(NotificationResponse::from)
                .toList();
    }
}

