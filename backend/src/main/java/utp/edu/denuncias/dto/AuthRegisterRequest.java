package utp.edu.denuncias.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import utp.edu.denuncias.enums.Rol;

/**
 * DTO utilizado para registrar un nuevo usuario en el sistema.
 * Contiene validaciones para asegurar que los datos ingresados sean correctos.
 */
public record AuthRegisterRequest(

        /** Nombres del usuario. Debe tener entre 3 y 50 caracteres. */
        @NotBlank(message = "El campo 'nombres' es obligatorio")
        @Size(min = 3, max = 50, message = "El campo 'nombres' debe tener entre 3 y 50 caracteres")
        String nombres,

        /** Apellidos del usuario. Debe tener entre 3 y 50 caracteres. */
        @NotBlank(message = "El campo 'apellidos' es obligatorio")
        @Size(min = 3, max = 50, message = "El campo 'apellidos' debe tener entre 3 y 50 caracteres")
        String apellidos,

        /** Nombre de usuario que utilizará para iniciar sesión. */
        @NotBlank(message = "El campo 'username' es obligatorio")
        String username,

        /** Contraseña del usuario. Oculta en las respuestas JSON. */
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank(message = "El campo 'password' es obligatorio")
        String password,

        /** Documento Nacional de Identidad. Exactamente 8 caracteres. */
        @NotBlank(message = "El campo 'dni' es obligatorio")
        @Size(min = 8, max = 8, message = "El campo 'dni' debe tener 8 caracteres")
        String dni,

        /** Correo electrónico del usuario. Debe tener formato válido. */
        @NotBlank(message = "El campo 'correo' es obligatorio")
        @Size(min = 5, max = 50, message = "El campo 'correo' debe tener entre 5 y 50 caracteres")
        @Email(message = "Formato de correo no aceptado.")
        String correo,

        /** Rol asignado al usuario (e.g., ADMIN, USUARIO). */
        @NotBlank(message = "El campo 'rol' es obligatorio")
        @Size(min = 3, max = 20, message = "El campo 'rol' debe tener entre 3 y 20 caracteres")
        @Enumerated(EnumType.STRING)
        Rol rol
) {}