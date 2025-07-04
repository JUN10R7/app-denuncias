package utp.edu.denuncias.dto;

import utp.edu.denuncias.enums.Estado;

import java.util.Arrays;
import java.util.List;

/**
 * DTO que representa un estado dentro del sistema.
 * Contiene el identificador único, título descriptivo y descripción detallada del estado.
 *
 * @param id          identificador único del estado
 * @param titulo      título descriptivo del estado
 * @param descripcion descripción detallada del estado
 */
public record EstadoDTO(String id, String titulo, String descripcion) {
    /**
     * Obtiene una lista de todos los estados disponibles en el sistema, representados como instancias de {@code EstadoDTO}.
     *
     * @return una lista de objetos {@code EstadoDTO} que contienen la información de todos los estados del sistema
     */
    public static List<EstadoDTO> all() {
        return Arrays.stream(Estado.values())
                .map(e -> new EstadoDTO(e.name(), e.getTitulo(), e.getDescripcion()))
                .toList();
    }
}