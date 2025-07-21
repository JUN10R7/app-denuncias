package utp.edu.denuncias.enums;

import lombok.Getter;

/**
 * Enumeración que define los diferentes tipos de solicitudes que pueden registrarse
 * en el sistema. Cada tipo de solicitud representa una acción específica que puede
 * ser requerida al sistema o a los usuarios responsables.
 */
@Getter
public enum TipoSolicitud {
    /**
     * Representa una solicitud para asignar un moderador responsable
     * a una denuncia específica. Esta acción es requerida cuando se necesita
     * designar un encargado para la gestión y resolución de la denuncia.
     */
    ASIGNAR_MODERADOR(
            "Solicitud de asignación de moderador",
            "Se solicita que se asigne un moderador responsable para esta denuncia."
    ),
    /**
     * Solicitud que permite requerir la actualización del estado actual de una denuncia.
     * Representa una acción destinada a modificar el estado asociado de acuerdo con su
     * progreso o situación específica.
     */
    CAMBIO_ESTADO(
            "Solicitud de cambio de estado",
            "Se solicita actualizar el estado actual de esta denuncia."
    ),
    /**
     * Solicitud que permite eliminar de forma permanente una denuncia registrada
     * en el sistema. Se utiliza cuando la denuncia no requiere más seguimiento
     * o debe ser eliminada por razones específicas.
     */
    ELIMINAR_DENUNCIA(
            "Solicitud de eliminación de denuncia",
            "Se solicita eliminar esta denuncia del sistema de forma permanente."
    ),
    /**
     * Tipo de solicitud que indica la petición de reapertura de una denuncia
     * previamente archivada o cerrada. Se utiliza para retomar el tratamiento
     * de casos que requieren revisión o acciones adicionales.
     */
    REABRIR_DENUNCIA(
            "Solicitud de reapertura de denuncia",
            "Se solicita reabrir una denuncia previamente archivada o cerrada."
    ),
    /**
     * Representa un mensaje interno de comunicación dentro del equipo,
     * generalmente utilizado para enviar observaciones o instrucciones
     * entre moderadores y administradores. Esta solicitud está destinada
     * únicamente al intercambio interno de información relevante para la
     * gestión de las denuncias o tareas administrativas.
     */
    INTERN_COMMUNICATION(
            "Mensaje interno del equipo",
            "Este mensaje contiene una observación o instrucción interna entre moderadores y administradores."
    );

    /**
     * Representa el título descriptivo asociado a un tipo específico de solicitud.
     * Este título proporciona una descripción corta y clara de la naturaleza de la
     * solicitud dentro del sistema.
     */
    private final String titulo;
    /**
     * Descripción detallada asociada a cada tipo de solicitud, que proporciona
     * información adicional sobre el propósito o la acción que representa.
     */
    private final String description;

    /**
     * Constructor para inicializar una instancia de TipoSolicitud con un título y una descripción específica.
     *
     * @param titulo el título descriptivo de la solicitud
     * @param description la descripción detallada de la solicitud
     */
    TipoSolicitud(String titulo, String description) {
        this.titulo = titulo;
        this.description = description;
    }
}


