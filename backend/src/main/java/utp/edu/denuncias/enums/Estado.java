package utp.edu.denuncias.enums;


/**
 * Enumeración que define los posibles estados de una denuncia.
 */
public enum Estado {
    /**
     * Representa el estado de una denuncia que aún no ha sido atendida, evaluada
     * o procesada. Indica que está pendiente de una acción inicial.
     */
    PENDIENTE,
    /**
     * Representa el estado de una denuncia que está siendo atendida o gestionada
     * en su proceso de resolución.
     */
    EN_PROCESO,
    /**
     * Representa el estado de una denuncia que está siendo evaluada o revisada
     * en profundidad, generalmente como parte de un análisis más detallado
     * antes de determinar el siguiente curso de acción.
     */
    EN_REVISION,
    /**
     * Representa el estado de una denuncia que ha sido resuelta, indicando que el
     * proceso ha concluido satisfactoriamente o se ha encontrado una solución.
     */
    RESUELTO,
    /**
     * Representa el estado de una denuncia que ha sido archivada, indicando que
     * el caso ha sido cerrado y no se encuentra activo para seguimiento o resolución
     * adicional.
     */
    ARCHIVADO,
    /**
     * Representa el estado de una denuncia que ha sido rechazada, indicando que
     * no se procederá con su resolución o no cumple con los criterios necesarios
     * para su gestión.
     */
    RECHAZADO
}