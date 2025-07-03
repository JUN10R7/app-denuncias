package utp.edu.denuncias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import utp.edu.denuncias.enums.TipoSolicitud;

/**
 * Registro (record) que representa una solicitud en el sistema.
 * Contiene los datos necesarios para crear o gestionar una solicitud relacionada a una denuncia.
 *
 * @param titulo título de la solicitud, debe ser obligatorio
 * @param msg mensaje o descripción de la solicitud, debe ser obligatorio
 * @param idDenuncia identificador de la denuncia asociada a la solicitud, debe ser obligatorio
 * @param idRevisor identificador opcional del revisor asignado a la solicitud
 * @param tipoSolicitud tipo de solicitud a realizar (revisar, archivar, eliminar, cambiar estado, declinar), debe ser obligatorio
 */
public record SolicitudRequest(
        @NotBlank(message = "El título es obligatorio")
        String titulo,

        @NotBlank(message = "El mensaje es obligatorio")
        String msg,

        @NotNull(message = "El ID de la denuncia es obligatorio")
        Long idDenuncia,

        Long idRevisor,

        @NotNull(message = "El tipo de solicitud es obligatorio")
        TipoSolicitud tipoSolicitud
) {
}
