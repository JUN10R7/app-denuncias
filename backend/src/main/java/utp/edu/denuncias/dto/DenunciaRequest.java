package utp.edu.denuncias.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import utp.edu.denuncias.enums.Categoria;

/**
 * Registro (record) que representa la solicitud para crear una denuncia.
 * Contiene los datos necesarios enviados desde el cliente para registrar una denuncia.
 */
public record DenunciaRequest (
        @NotBlank(message = "El campo 'titulo' es obligatorio")
        String titulo,
        @NotBlank(message = "El campo 'description' es obligatorio")
        String description,
        @NotBlank(message = "El campo 'lugar' es obligatorio")
        String lugar,
        @NotBlank(message = "El campo 'categoria' es obligatorio")
        @Enumerated(EnumType.STRING)  // Indica que el enum se debe manejar como String
        Categoria categoria
) {}