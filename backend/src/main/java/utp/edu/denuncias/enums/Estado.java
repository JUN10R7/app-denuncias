package utp.edu.denuncias.enums;

import lombok.Getter;

/**
 * Enumeración que define los diferentes estados que puede tener una denuncia
 * dentro del sistema. Cada estado representa una etapa específica en el
 * flujo de gestión de las denuncias.
 */
@Getter
public enum Estado {
    /**
     * Representa el estado inicial de una denuncia en el sistema.
     * Indica que la denuncia ha sido registrada pero aún no ha recibido
     * atención o gestión por parte del equipo responsable.
     */
    PENDIENTE("Pendiente", "La denuncia aún no ha sido atendida."),
    /**
     * Estado que indica que la denuncia está en una etapa de gestión activa.
     * Representa el momento en que se están llevando a cabo acciones para
     * resolver la denuncia o procesar la información proporcionada.
     */
    EN_PROCESO("En proceso", "La denuncia está siendo gestionada."),
    /**
     * Representa el estado en el que la denuncia está siendo evaluada en detalle
     * para determinar los próximos pasos a seguir. Indica que se encuentra en
     * una etapa analítica del proceso de gestión.
     */
    EN_REVISION("En revisión", "La denuncia está siendo evaluada en detalle."),
    /**
     * Estado que indica que la denuncia ha sido solucionada satisfactoriamente.
     */
    RESUELTO("Resuelto", "La denuncia ha sido solucionada satisfactoriamente."),
    /**
     * Estado que indica que la denuncia no será atendida o no cumple con
     * los criterios establecidos para su gestión dentro del sistema.
     */
    RECHAZADO("Rechazado", "La denuncia no será atendida o no cumple criterios."),
    /**
     * Estado que indica que la denuncia se encuentra almacenada en la base de datos,
     * pero solo será accesible para usuarios o roles específicamente autorizados.
     */
    ELIMINADO("Eliminado", "La denuncia se almacenará en la base de datos pero será accesible para seleccionados.");

    /**
     * Representa el título descriptivo asociado a un elemento dentro de la enumeración.
     * Proporciona una breve identificación textual que describe de forma clara la naturaleza
     * o propósito del elemento.
     */
    private final String titulo;
    /**
     * Representa una descripción detallada asociada a un elemento específico
     * dentro del sistema. Proporciona información adicional que explica o
     * complementa el propósito y el significado del elemento al que está asociada.
     */
    private final String descripcion;

    /**
     * Constructor del enum Estado que inicializa un estado con su título y descripción.
     *
     * @param titulo el título descriptivo del estado
     * @param descripcion una descripción detallada del estado
     */
    Estado(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }
}
