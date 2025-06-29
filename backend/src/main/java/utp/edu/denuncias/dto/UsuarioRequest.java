package utp.edu.denuncias.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import utp.edu.denuncias.enums.Rol;

/**
 * DTO utilizado para registrar un nuevo usuario en el sistema.
 * Contiene validaciones para asegurar que los datos ingresados sean correctos.
 */
@Builder
public record UsuarioRequest(

        @NotBlank(message = "El campo 'nombres' es obligatorio")
        @Size(min = 3, max = 50, message = "El campo 'nombres' debe tener entre 3 y 50 caracteres")
        String nombres,

        @NotBlank(message = "El campo 'apellidos' es obligatorio")
        @Size(min = 3, max = 50, message = "El campo 'apellidos' debe tener entre 3 y 50 caracteres")
        String apellidos,

        @NotBlank(message = "El campo 'username' es obligatorio")
        String username,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank(message = "El campo 'password' es obligatorio")
        String password,

        @NotBlank(message = "El campo 'dni' es obligatorio")
        @Size(min = 8, max = 8, message = "El campo 'dni' debe tener 8 caracteres")
        String dni,

        @NotBlank(message = "El campo 'email' es obligatorio")
        @Size(min = 5, max = 50, message = "El campo 'email' debe tener entre 5 y 50 caracteres")
        @Email(message = "Formato de correo no aceptado.")
        String email,

        @NotBlank(message = "El campo 'rol' es obligatorio")
        @Size(min = 3, max = 20, message = "El campo 'rol' debe tener entre 3 y 20 caracteres")
        @Enumerated(EnumType.STRING)
        Rol rol
) {}