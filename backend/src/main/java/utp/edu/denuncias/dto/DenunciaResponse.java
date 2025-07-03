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
    public static DenunciaResponse from(Denuncia denuncia) {
        return DenunciaResponse.builder()
                .titulo(denuncia.getTitulo())
                .description(denuncia.getDescription())
                .lugar(denuncia.getLugar())
                .categoria(denuncia.getCategoria().toString())
                .estado(denuncia.getEstado().toString())
                .fecha(denuncia.getCreatedDate().toString())
                .usuario(denuncia.getUsuario().getUsername())
                .mod(denuncia.getModAsignado().getUsername() == null ? "Sin asignar" : denuncia.getModAsignado().getUsername())
                .build();
    }
    public static List<DenunciaResponse> from(List<Denuncia> denuncias) {
        return denuncias.stream()
                .map(DenunciaResponse::from)
                .toList();
    }
}