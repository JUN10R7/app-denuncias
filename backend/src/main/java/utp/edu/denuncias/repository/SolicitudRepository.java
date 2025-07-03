package utp.edu.denuncias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utp.edu.denuncias.model.Solicitud;

/**
 * Repositorio JPA para la entidad Solicitud.
 * Proporciona métodos para realizar operaciones CRUD básicas sobre solicitudes.
 *
 * Esta interfaz permite interactuar con la base de datos
 * y realizar consultas relacionadas con las solicitudes registradas en el sistema.
 */
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {


}
