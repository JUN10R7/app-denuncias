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
        String titulo,
        String description,
        String lugar,
        String categoria,
        String estado,
        String fecha,
        String usuario,
        String mod
) {
    /**
     * Convierte una instancia de la entidad Denuncia en un objeto DenunciaResponse.
     *
     * @param denuncia instancia de la entidad Denuncia a convertir.
     * @return una instancia de DenunciaResponse que contiene los datos estructurados de la denuncia.
     */
    public static DenunciaResponse from(Denuncia denuncia) {
        return DenunciaResponse.builder()
                .titulo(denuncia.getTitulo())
                .description(denuncia.getDescription())
                .lugar(denuncia.getLugar())
                .categoria(denuncia.getCategoria().name())
                .estado(denuncia.getEstado().name())
                .fecha(denuncia.getCreatedDate().toString())
                .usuario(denuncia.getUsuario().getUsername())
                .mod(denuncia.getModAsignado().getUsername() == null ? "Sin asignar" : denuncia.getModAsignado().getUsername())
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