package utp.edu.denuncias.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import utp.edu.denuncias.enums.Rol;

/**
 * Registro (record) que representa la solicitud para actualizar los datos de un usuario.
 * Incluye validaciones sobre la longitud de algunos campos y que el DNI no esté en blanco.
 */
@Builder
public record UsuarioUpdateRequest(
        @Size(min = 3, max = 50, message = "El campo 'username' debe tener entre 3 y 50 caracteres")
        String username,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @Size(min = 3, max = 50, message = "El campo 'nombres' debe tener entre 3 y 50 caracteres")
        String password,

        @Size(min = 5, max = 50, message = "El campo 'email' debe tener entre 5 y 50 caracteres")
        @Email(message = "Formato de correo no aceptado.")
        String email,

        @Size(min = 3, max = 20, message = "El campo 'rol' debe tener entre 3 y 20 caracteres")
        @Enumerated(EnumType.STRING)
        Rol rol
) {
}