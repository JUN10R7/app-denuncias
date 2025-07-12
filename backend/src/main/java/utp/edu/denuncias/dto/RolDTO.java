package utp.edu.denuncias.dto;

import utp.edu.denuncias.enums.Rol;

import java.util.Arrays;
import java.util.List;

/**
 * DTO que representa un rol dentro del sistema.
 * Contiene el identificador único, el título descriptivo y una descripción detallada del rol.
 *
 * @param id          identificador único del rol
 * @param nombre      título descriptivo del rol
 * @param description descripción detallada del rol
 */
public record RolDTO(String id, String nombre, String description) {
    /**
     * Obtiene una lista de todos los roles disponibles en el sistema, representados como instancias de {@code RolDTO}.
     *
     * @return una lista de objetos {@code RolDTO} que contienen la información de todos los roles del sistema
     */
    public static List<RolDTO> all() {
        return Arrays.stream(Rol.values())
                .map(r -> new RolDTO(r.name(), r.getTitulo(), r.getDescripcion()))
                .toList();
    }
}
