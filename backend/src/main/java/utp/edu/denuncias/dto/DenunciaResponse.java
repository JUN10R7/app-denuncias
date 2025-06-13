package utp.edu.denuncias.dto;
import lombok.Builder;

/**
 * DTO (Data Transfer Object) que representa la respuesta de una denuncia.
 * Utilizado para enviar datos estructurados desde el backend hacia el cliente.
 */

@Builder
public record DenunciaResponse(

        /** Título de la denuncia */
        String titulo,

        /** Descripción detallada de la denuncia */
        String descripcion,

        /** Lugar o ubicación donde ocurrió la denuncia */
        String lugar,

        /** Categoría de la denuncia (e.g., violencia, robo, etc.) */
        String categoria,

        /** Estado actual de la denuncia (e.g., Registrada, En proceso, Cerrada) */
        String estado,

        /** Nombre de usuario del ciudadano que registró la denuncia */
        String username,

        /** Fecha en que se registró la denuncia */
        String fecha
) {
}