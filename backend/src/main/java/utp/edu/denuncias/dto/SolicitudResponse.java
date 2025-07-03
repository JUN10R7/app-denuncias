package utp.edu.denuncias.dto;

import lombok.Builder;
import utp.edu.denuncias.model.Solicitud;

/**
 * Representa la respuesta para una solicitud en el sistema, encapsulando
 * información relevante como su identificador, detalles, estado y metadatos.
 */
@Builder
public record SolicitudResponse(
        Long id,
        String titulo,
        String mensaje,
        String respuesta,
        String estado,
        String tipo,
        String createdDate,
        String endDate,
        String autor,
        String revisor
) {
    /**
     * Convierte una instancia de la entidad Solicitud a un objeto SolicitudResponse.
     * Agrega todos los campos relevantes de la Solicitud al nuevo objeto de respuesta.
     *
     * @param solicitud la instancia de Solicitud que se desea convertir.
     * @return el objeto SolicitudResponse creado a partir de los datos de la solicitud proporcionada.
     */
    public static SolicitudResponse from(Solicitud solicitud) {
        return SolicitudResponse.builder()
                .id(solicitud.getId())
                .titulo(solicitud.getTitulo())
                .mensaje(solicitud.getMsg())
                .respuesta(solicitud.getRespuesta())
                .estado(solicitud.getEstado().toString())
                .tipo(solicitud.getTipoSolicitud().toString())
                .createdDate(solicitud.getCreatedDate().toString())
                .endDate(solicitud.getEndDate().toString())
                .autor(solicitud.getAutor().getUsername())
                .revisor(solicitud.getRevisor().getUsername())
                .build();
    }
}
