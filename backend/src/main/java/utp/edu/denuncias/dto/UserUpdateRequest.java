package utp.edu.denuncias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Registro (record) que representa la solicitud para actualizar los datos de un usuario.
 * Incluye validaciones sobre la longitud de algunos campos y que el DNI no esté en blanco.
 */
@Builder  // Permite construir instancias de UserUpdateRequest usando el patrón Builder de Lombok
public record UserUpdateRequest (
        /**
         * Nombres del usuario.
         * Debe tener entre 3 y 50 caracteres.
         */
        @Size(min = 3, max = 50, message = "El campo 'nombres' debe tener entre 3 y 50 caracteres")
        String nombres,

        /**
         * Apellidos del usuario.
         * Debe tener entre 3 y 50 caracteres.
         */
        @Size(min = 3, max = 50, message = "El campo 'nombres' debe tener entre 3 y 50 caracteres")
        String apellidos,

        /**
         * Contraseña del usuario.
         * Debe tener entre 3 y 50 caracteres.
         */
        @Size(min = 3, max = 50, message = "El campo 'nombres' debe tener entre 3 y 50 caracteres")
        String password,

        /**
         * Documento Nacional de Identidad (DNI).
         * Campo obligatorio que no puede estar vacío ni en blanco.
         */
        @NotBlank
        String dni
) {
}