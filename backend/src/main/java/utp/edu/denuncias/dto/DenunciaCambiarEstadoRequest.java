package utp.edu.denuncias.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import utp.edu.denuncias.enums.Estado;

public record DenunciaCambiarEstadoRequest(
        @NotBlank(message = "El campo 'id' es obligatorio")
        Long id,
        @NotBlank(message = "El campo 'categoria' es obligatorio")
        @Enumerated(EnumType.STRING)
        Estado estado
) {
}
