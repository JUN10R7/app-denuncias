package utp.edu.denuncias.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Registro (record) que representa una solicitud para la revisión de una acción
 * relacionada con una denuncia, la cual puede ser aprobada o rechazada.
 *
 * @param id identificador único de la solicitud, debe ser obligatorio
 * @param aprobado indica si la solicitud fue aprobada (true) o rechazada (false), debe ser obligatorio
 * @param mensajeRespuesta mensaje opcional que describe la razón de la aprobación o rechazo
 */
public record SolicitudRevisionRequest(

        Long id,

        Long idDenuncia,

        Long idModerador,

        Long idMod,

        @NotNull(message = "Debes indicar si es aprobación o rechazo")
        boolean aprobado,

        @NotNull(message = "El mensaje es obligatorio")
        String mensajeRespuesta
) {}
