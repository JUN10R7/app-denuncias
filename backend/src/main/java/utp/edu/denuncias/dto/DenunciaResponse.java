package utp.edu.denuncias.dto;
import lombok.Builder;
import utp.edu.denuncias.model.Denuncia;

import java.util.List;

/**
 * DTO (Data Transfer Object) que representa la respuesta de una denuncia.
 * Utilizado para enviar datos estructurados desde el backend hacia el cliente.
 */

@Builder
public record DenunciaResponse(
        Long id,
        String titulo,
        String description,
        String lugar,
        String categoria,
        String estado,
        String fecha,
        String usuario,
        String username,
        Long idUsuario,
        String mod,
        Long idMod
) {
    /**
     * Convierte una instancia de la entidad Denuncia en un objeto DenunciaResponse.
     *
     * @param denuncia instancia de la entidad Denuncia a convertir.
     * @return una instancia de DenunciaResponse que contiene los datos estructurados de la denuncia.
     */
    public static DenunciaResponse from(Denuncia denuncia) {
        return DenunciaResponse.builder()
                .id(denuncia.getId())
                .titulo(denuncia.getTitulo())
                .description(denuncia.getDescription())
                .lugar(denuncia.getLugar())
                .categoria(denuncia.getCategoria().name())
                .estado(denuncia.getEstado().name())
                .fecha(denuncia.getCreatedDate().toString())
                .usuario(denuncia.getUsuario().getNombres() + " " + denuncia.getUsuario().getApellidos())
                .username(denuncia.getUsuario().getUsername())
                .idUsuario(denuncia.getUsuario().getId())
                .mod(denuncia.getModAsignado() == null ? null : denuncia.getModAsignado().getNombres() + " " + denuncia.getModAsignado().getApellidos())
                .idMod(denuncia.getModAsignado() == null ? null : denuncia.getModAsignado().getId())
                .build();
    }
    /**
     * Convierte una lista de objetos de tipo {@code Denuncia} en una lista
     * de objetos de tipo {@code DenunciaResponse}.
     *
     * @param denuncias lista de objetos {@code Denuncia} a transformar
     * @return una lista de objetos {@code DenunciaResponse} generados a partir de las denuncias proporcionadas
     */
    public static List<DenunciaResponse> from(List<Denuncia> denuncias) {
        return denuncias.stream()
                .map(DenunciaResponse::from)
                .toList();
    }
}