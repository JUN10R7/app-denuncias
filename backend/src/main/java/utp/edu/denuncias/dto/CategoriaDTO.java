package utp.edu.denuncias.dto;

import utp.edu.denuncias.enums.Categoria;

import java.util.Arrays;
import java.util.List;

/**
 * DTO que representa una categoría dentro del sistema.
 * Contiene el identificador único, el título descriptivo y una descripción detallada de la categoría.
 *
 * @param id          identificador único de la categoría
 * @param nombre      título descriptivo de la categoría
 * @param description descripción detallada de la categoría
 */
public record CategoriaDTO(String id, String nombre, String description) {
    /**
     * Obtiene una lista de todas las categorías disponibles en el sistema,
     * representadas como instancias de {@code CategoriaDTO}.
     *
     * @return una lista de objetos {@code CategoriaDTO} que contienen la información
     *         de todas las categorías del sistema
     */
    public static List<CategoriaDTO> all() {
        return Arrays.stream(Categoria.values())
                .map(c -> new CategoriaDTO(c.name(), c.getTitulo(), c.getDescription()))
                .toList();
    }
}

