package utp.edu.denuncias.model;


import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utp.edu.denuncias.enums.Estado;
import utp.edu.denuncias.enums.TipoSolicitud;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solicitud {

    /**
     * Identificador único de la solicitud.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Título de la solicitud.
     * Representa una breve descripción o encabezado
     * que resume el contenido o propósito de la solicitud.
     */
    private String titulo;

    /**
     * Mensaje descriptivo asociado a la solicitud.
     * Este atributo puede contener información relevante relacionada
     * con la solicitud creada por el usuario.
     */
    private String msg;

    /**
     * Representa la respuesta asociada a la solicitud.
     * Este campo puede ser utilizado para almacenar información
     * sobre la resolución o el comentario en respuesta a la misma.
     * Por defecto, su valor inicial es null.
     */
    @Builder.Default
    private String respuesta = null;

    /**
     * Relación muchos a uno con la entidad Denuncia.
     * Representa la denuncia asociada a esta solicitud.
     * Este vínculo se define a través de la columna "denuncia_id" en la base de datos.
     */
    @ManyToOne
    @JoinColumn(name = "denuncia_id")
    private Denuncia denuncia;

    /**
     * Representa al usuario que actúa como autor de la solicitud.
     * Define una relación de muchos a uno con la entidad Usuario.
     */
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    /**
     * Usuario asignado como revisor de la solicitud actual.
     * Representa una relación muchos a uno con la entidad Usuario.
     * El revisor tiene la responsabilidad de revisar y gestionar la solicitud.
     */
    @ManyToOne
    @JoinColumn(name = "revisor_id", nullable = true)
    private Usuario revisor;

    /**
     * Representa el estado actual de la solicitud.
     * Se almacena como un valor de la enumeración Estado.
     * Por defecto, su valor inicial es Estado PENDIENTE.
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.PENDIENTE;

    /**
     * Define el tipo de solicitud asociada a la entidad Solicitud.
     * Representa una acción concreta que el sistema debe procesar,
     * tales como revisar, archivar, eliminar, cambiar el estado o rechazar.
     * Su valor se almacena como una cadena que corresponde a los valores definidos en el enum TipoSolicitud.
     */
    @Enumerated(EnumType.STRING)
    private TipoSolicitud tipoSolicitud;

    /**
     * Fecha y hora en que se creó la solicitud.
     * Se inicializa automáticamente con la fecha y hora actuales al momento de crear la instancia.
     */
    @Builder.Default
    private LocalDateTime createdDate = LocalDateTime.now();

    /**
     * Fecha y hora en que se considera terminada o finalizada la solicitud.
     * Este campo es opcional y su valor inicial es null, lo que indica que
     * la solicitud no tiene una fecha de finalización definida al momento de su creación.
     */
    @Builder.Default
    private LocalDateTime endDate = null;
}
