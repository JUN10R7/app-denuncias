package utp.edu.denuncias.dto;

import lombok.Builder;
import utp.edu.denuncias.model.Solicitud;

import java.util.List;

/**
 * Representa la respuesta para una solicitud en el sistema, encapsulando
 * información relevante como su identificador, detalles, estado y metadatos.
 */
@Builder
public record SolicitudResponse(
        Long id,
        String titulo,
        String msg,
        String respuesta,
        String estado,
        String tipoSolicitud,
        String createdDate,
        String endDate,
        String autor,
        Long idDenuncia,
        Long idAutor,
        String revisor,
        Long idRevisor
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
                .msg(solicitud.getMsg())
                .respuesta(solicitud.getRespuesta())
                .estado(solicitud.getEstado().name())
                .tipoSolicitud(solicitud.getTipoSolicitud().name())
                .createdDate(solicitud.getCreatedDate().toString())
                .endDate(solicitud.getEndDate().toString())
                .idDenuncia(solicitud.getDenuncia() == null ? null : solicitud.getDenuncia().getId())
                .autor(solicitud.getAutor().getNombres() + " " + solicitud.getAutor().getApellidos())
                .idAutor(solicitud.getAutor().getId())
                .revisor(solicitud.getRevisor() == null ? null : solicitud.getRevisor().getNombres() + " " + solicitud.getRevisor().getApellidos())
                .idRevisor(solicitud.getRevisor() == null ? null : solicitud.getRevisor().getId())
                .build();
    }

    /**
     * Convierte una lista de entidades {@code Solicitud} a una lista de objetos {@code SolicitudResponse}.
     *
     * @param solicitudes la lista de objetos {@code Solicitud} que se desea convertir.
     * @return una lista de objetos {@code SolicitudResponse} creada a partir de los datos de las solicitudes proporcionadas.
     */
    public static List<SolicitudResponse> from(List<Solicitud> solicitudes) {
        return solicitudes.stream()
                .map(SolicitudResponse::from)
                .toList();
    }
}
