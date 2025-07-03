package utp.edu.denuncias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    /**
     * Identificador único de la notificación.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Título asociado a la notificación.
     * Representa un breve resumen o encabezado de la información contenida en la notificación.
     */
    private String titulo;

    /**
     * Mensaje asociado a la notificación.
     * Este campo contiene información textual relevante que puede ser enviada
     * o mostrada como parte de una notificación en el sistema.
     */
    private String message;

    /**
     * Indica si la notificación ha sido leída.
     * Este atributo se inicializa con el valor false, indicando que por defecto
     * las notificaciones se consideran no leídas al momento de su creación.
     */
    @Builder.Default
    private boolean read = false;

    /**
     * Fecha y hora en la que se creó la instancia de Notification.
     * Se inicializa automáticamente con la fecha y hora actuales al momento de su creación.
     */
    @Builder.Default
    private LocalDateTime createdDate = LocalDateTime.now();

    /**
     * Relación muchos a uno con la entidad Denuncia.
     * Representa la denuncia asociada a una notificación específica.
     * Se une mediante la columna "denuncia_id" en la tabla Notification.
     */
    @ManyToOne
    @JoinColumn(name = "denuncia_id")
    private Denuncia denuncia;

    /**
     * Relación muchos a uno con la entidad Solicitud.
     * Representa una solicitud asociada a una notificación específica.
     * Se une mediante la columna "solicitud_id" en la tabla Notification.
     */
    @ManyToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    /**
     * Relación muchos a uno con la entidad Usuario.
     * Representa el usuario que es el destinatario de esta notificación.
     * Se une mediante la columna "usuario_id" en la tabla Notification.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario destinatario;

}
