package utp.edu.denuncias.dto;

import java.time.LocalDateTime;

/**
 * Registro (record) que representa la estructura estándar para enviar respuestas de error en la API.
 * Contiene información relevante sobre el error ocurrido.
 */
public record ErrorResponse(
        /**
         * Mensaje descriptivo del error que se quiere comunicar.
         */
        String message,

        /**
         * Tipo o nombre del error (por ejemplo, "NotFoundException", "ValidationError", etc.).
         */
        String error,

        /**
         * Código HTTP del estado de la respuesta (por ejemplo, 404, 500).
         */
        int status,

        /**
         * Marca de tiempo (fecha y hora) en que ocurrió el error.
         */
        LocalDateTime timestamp
) {
    /**
     * Constructor secundario que permite crear un ErrorResponse
     * sin necesidad de proporcionar el timestamp, asignándolo automáticamente a la fecha y hora actual.
     *
     * @param message mensaje descriptivo del error
     * @param error tipo o nombre del error
     * @param status código HTTP del estado
     */
    public ErrorResponse(String message, String error, int status) {
        this(message, error, status, LocalDateTime.now());
    }
}