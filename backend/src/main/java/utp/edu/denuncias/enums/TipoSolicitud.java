package utp.edu.denuncias.enums;

public enum TipoSolicitud {
    /**
     * Representa una solicitud para revisar un caso, documento o denuncia.
     * Esta acción sugiere que el elemento debe ser analizado o evaluado
     * antes de tomar una decisión o acción adicional.
     */
    REVISAR,
    /**
     * Representa una solicitud para archivar un elemento o registro.
     * Generalmente indica que el elemento ya no está activo, pero
     * se mantiene almacenado para referencia o historial.
     */
    ARCHIVAR,
    /**
     * Representa el tipo de solicitud para eliminar un elemento.
     * Este tipo de solicitud se utiliza cuando se requiere eliminar
     * o borrar el elemento correspondiente del sistema.
     */
    ELIMINAR,
    /**
     * Representa un tipo de solicitud destinada a modificar el estado actual
     * de una denuncia dentro del sistema. Este tipo de solicitud implica
     * una actualización en la etapa o condición asociada a la misma.
     */
    CAMBIAR_ESTADO,
    /**
     * Representa una solicitud para declinar un elemento o acción en el sistema.
     * Este tipo de solicitud indica que el elemento no será aceptado ni procesado.
     */
    DECLINAR
}
