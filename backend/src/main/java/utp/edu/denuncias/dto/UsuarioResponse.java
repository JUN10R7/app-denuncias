package utp.edu.denuncias.dto;

import lombok.Builder;
import utp.edu.denuncias.model.Usuario;

import java.util.List;

@Builder
public record UsuarioResponse(
        Long id,
        String nombres,
        String apellidos,
        String username,
        String email,
        String dni,
        String rol,
        boolean enabled
) {
    /**
     * Crea una instancia de {@code UsuarioResponse} a partir de un objeto {@code Usuario}.
     *
     * @param usuario el objeto {@code Usuario} desde el cual se extraerán los datos
     * @return una instancia de {@code UsuarioResponse} con los datos extraídos del {@code Usuario} proporcionado
     */
    public static UsuarioResponse from(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .dni(usuario.getDni())
                .rol(usuario.getRol().name())
                .enabled(usuario.isEnabled())
                .build();
    }
    /**
     * Convierte una lista de objetos {@code Usuario} en una lista correspondiente de objetos {@code UsuarioResponse}.
     *
     * @param usuarios la lista de objetos {@code Usuario} que se desea convertir
     * @return una lista de objetos {@code UsuarioResponse} generados a partir de los datos de los objetos {@code Usuario} proporcionados
     */
    public static List<UsuarioResponse> from(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(UsuarioResponse::from)
                .toList();
    }
}
