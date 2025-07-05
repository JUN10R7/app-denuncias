package utp.edu.denuncias.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import utp.edu.denuncias.dto.SolicitudRequest;
import utp.edu.denuncias.dto.SolicitudResponse;
import utp.edu.denuncias.dto.SolicitudRevisionRequest;
import utp.edu.denuncias.service.SolicitudService;

import java.util.List;

/**
 * Controlador para gestionar las solicitudes en el sistema.
 * Proporciona endpoints para la creación y revisión de solicitudes, interactuando
 * con la lógica de negocio a través de {@link SolicitudService}.
 */
@EnableMethodSecurity
@RestController
@RequestMapping("/api/solicitud")
@RequiredArgsConstructor
public class SolicitudController {

    /**
     * Instancia del servicio {@link SolicitudService} utilizada para gestionar la lógica de negocio
     * relacionada con las solicitudes. Proporciona métodos para realizar y revisar solicitudes,
     * interactuando con los datos persistidos en el sistema.
     */
    private final SolicitudService solicitudService;

    /**
     * Recupera una solicitud específica a partir de su identificador único.
     *
     * @param id Identificador único de la solicitud a recuperar.
     * @return una instancia de {@link ResponseEntity} que contiene un {@link SolicitudResponse}
     *         con los datos de la solicitud encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponse> getSolicitud(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.listarSolicitud(id));
    }

    /**
     * Obtiene una lista de todas las solicitudes disponibles en el sistema.
     *
     * @return un {@link ResponseEntity} que contiene una lista de objetos {@link SolicitudResponse}
     *         con los detalles de todas las solicitudes existentes.
     */
    @GetMapping("/listar")
    public ResponseEntity<List<SolicitudResponse>> listarSolicitudes() {
        return ResponseEntity.ok(solicitudService.listarSolicitudes());
    }

    /**
     * Lista todas las solicitudes que no tienen un revisor asignado y
     * las devuelve en orden descendente por fecha de creación.
     *
     * @return una entidad {@code ResponseEntity} que contiene una lista de objetos {@code SolicitudResponse},
     *         que representan las solicitudes sin revisor asignado.
     */
    @GetMapping("/admin/listar")
    public ResponseEntity<List<SolicitudResponse>> listarSolicitudesAdmin() {
        return ResponseEntity.ok(solicitudService.listarSolicitudesAdmin());
    }

    /**
     * Lista las solicitudes asociadas a una denuncia específica identificada por su ID.
     *
     * @param id identificador único de la denuncia cuyas solicitudes se desean listar.
     * @return una respuesta HTTP con una lista de objetos {@link SolicitudResponse} que representan
     *         las solicitudes relacionadas con la denuncia especificada.
     */
    @GetMapping("/admin/denuncia/{id}")
    public ResponseEntity<List<SolicitudResponse>> listarSolicitudesDenuncia(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.listarSolicitudesDenuncia(id));
    }

    /**
     * Maneja la creación de una nueva solicitud en el sistema utilizando los datos proporcionados.
     *
     * @param request objeto {@link SolicitudRequest} que contiene la información necesaria para crear la solicitud,
     *                incluyendo título, mensaje, ID de la denuncia, ID del revisor opcional y el tipo de solicitud.
     */
    @PostMapping
    public void realizarSolicitud(@RequestBody SolicitudRequest request) {
        solicitudService.realizarSolicitud(request);
    }

    /**
     * Acepta o rechaza una solicitud de revisión en función de los datos proporcionados.
     * Este método actualiza el estado de la solicitud en el sistema y retorna una respuesta
     * con los detalles de la misma.
     *
     * @param request un objeto {@link SolicitudRevisionRequest} que contiene el ID de la solicitud,
     *                el estado de aprobación o rechazo, y un mensaje opcional de respuesta.
     * @return un {@link ResponseEntity} que contiene un {@link SolicitudResponse} con los datos
     *         actualizados de la solicitud procesada.
     */
    @PutMapping
    public ResponseEntity<SolicitudResponse> aceptarSolicitud(@RequestBody SolicitudRevisionRequest request) {
        return ResponseEntity.ok(solicitudService.revisarSolicitud(request));
    }
}
