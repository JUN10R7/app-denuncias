package utp.edu.denuncias.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO utilizado para autenticar a un usuario en el sistema.
 * Contiene las credenciales requeridas para el inicio de sesión.
 */
public record AuthRequest(

        /** Nombre de usuario del sistema. Es obligatorio. */
        @NotBlank(message = "El campo 'username' es obligatorio")
        String username,

        /**
         * Contraseña del usuario. Es obligatoria y se oculta en la salida JSON.
         */
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotBlank(message = "El campo 'password' es obligatorio")
        String password
) {}