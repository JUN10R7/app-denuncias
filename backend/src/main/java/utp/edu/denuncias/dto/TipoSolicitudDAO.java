package utp.edu.denuncias.dto;

import utp.edu.denuncias.enums.TipoSolicitud;

import java.util.Arrays;
import java.util.List;

/**
 * Registro (record) que representa un objeto DAO (Data Access Object) relacionado con
 * el tipo de solicitudes dentro del sistema.
 * Contiene información básica asociada al tipo de solicitud, como su nombre,
 * título descriptivo y descripción detallada.
 */
public record TipoSolicitudDAO(String name, String titulo, String description) {
    /**
     * Obtiene una lista de todas las solicitudes disponibles en el sistema,
     * representadas como instancias de {@code TipoSolicitudDAO}.
     *
     * @return una lista de objetos {@code TipoSolicitudDAO} que contienen la información
     *         de todas las solicitudes disponibles en el sistema
     */
    public static List<TipoSolicitudDAO> all() {
        return Arrays.stream(TipoSolicitud.values())
                .map(t -> new TipoSolicitudDAO(t.name(), t.getTitulo(), t.getDescription()))
                .toList();
    }
}