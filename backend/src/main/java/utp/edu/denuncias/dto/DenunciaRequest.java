package utp.edu.denuncias.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

/**
 * Registro (record) que representa la solicitud para crear una denuncia.
 * Contiene los datos necesarios enviados desde el cliente para registrar una denuncia.
 */
public record DenunciaRequest (
        /**
         * Título de la denuncia.
         * No puede estar en blanco.
         */
        @NotBlank(message = "El campo 'titulo' es obligatorio")
        String titulo,

        /**
         * Descripción detallada de la denuncia.
         * No puede estar en blanco.
         */
        @NotBlank(message = "El campo 'descripcion' es obligatorio")
        String descripcion,

        /**
         * Lugar donde ocurrió el hecho denunciado.
         * No puede estar en blanco.
         */
        @NotBlank(message = "El campo 'lugar' es obligatorio")
        String lugar,

        /**
         * Categoría a la que pertenece la denuncia.
         * Utiliza una enumeración y se guarda como String.
         * No puede estar en blanco.
         */
        @NotBlank(message = "El campo 'categoria' es obligatorio")
        @Enumerated(EnumType.STRING)  // Indica que el enum se debe manejar como String
        Categoria categoria,

        /**
         * Nombre de usuario que realiza la denuncia.
         * No puede estar en blanco.
         */
        @NotBlank(message = "El campo 'user' es obligatorio")
        String username
) {
        /**
         * Enumeración que define las categorías válidas para la denuncia.
         */
        public enum Categoria {
                VIOLENCIA, ROBO, CORRUPCION, DROGAS, ACOSO, CONTAMINACION
        }
}