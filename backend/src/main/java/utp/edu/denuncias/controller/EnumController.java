package utp.edu.denuncias.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import utp.edu.denuncias.dto.CategoriaDTO;
import utp.edu.denuncias.dto.EstadoDTO;
import utp.edu.denuncias.dto.RolDTO;
import utp.edu.denuncias.dto.TipoSolicitudDAO;

import java.util.List;

/**
 * Controlador REST para la gestión y recuperación de listas de enumeraciones
 * como roles, categorías, estados y tipos de solicitud disponibles en el sistema.
 */
@RestController("/enum")
public class EnumController {

    /**
     * Obtiene todos los roles disponibles en el sistema.
     *
     * @return una respuesta HTTP que contiene una lista de objetos {@code RolDTO} representando los roles disponibles
     */
    @GetMapping("/roles")
    public ResponseEntity<List<RolDTO>> getAllRoles() {
        return ResponseEntity.ok(RolDTO.all());
    }

    /**
     * Endpoint para obtener todas las categorías disponibles en el sistema.
     * Devuelve una lista con las categorías representadas como objetos {@code CategoriaDTO}.
     *
     * @return una {@code ResponseEntity} que contiene una lista de {@code CategoriaDTO}
     *         con la información de todas las categorías del sistema
     */
    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> geAllCategorias() {
        return ResponseEntity.ok(CategoriaDTO.all());
    }

    /**
     * Recupera una lista de todos los estados disponibles en el sistema.
     *
     * @return una respuesta HTTP que contiene una lista de objetos {@code EstadoDTO} con
     *         la información de todos los estados existentes en el sistema
     */
    @GetMapping("/estados")
    public ResponseEntity<List<EstadoDTO>> getAllEstados() {
        return ResponseEntity.ok(EstadoDTO.all());
    }

    /**
     * Obtiene una lista con todos los tipos de solicitud disponibles en el sistema.
     *
     * @return un {@code ResponseEntity} que contiene una lista de objetos {@code TipoSolicitudDAO},
     *         representando los tipos de solicitud disponibles.
     */
    @GetMapping("/tiposSolicitud")
    public ResponseEntity<List<TipoSolicitudDAO>> getAllTiposSolicitud() {
        return ResponseEntity.ok(TipoSolicitudDAO.all());
    }
}
