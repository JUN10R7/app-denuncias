package utp.edu.denuncias.dto;
import lombok.Builder;

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

        String username,

        String fecha
) {
}