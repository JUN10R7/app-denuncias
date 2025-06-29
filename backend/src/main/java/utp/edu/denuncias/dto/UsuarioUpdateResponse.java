package utp.edu.denuncias.dto;

import lombok.Builder;

@Builder
public record UsuarioUpdateResponse(
        String message,
        String nombres,
        String apellidos,
        String username,
        String email,
        String dni,
        String rol
) {
}
