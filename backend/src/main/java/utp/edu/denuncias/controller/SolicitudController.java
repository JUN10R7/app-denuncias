package utp.edu.denuncias.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import utp.edu.denuncias.dto.SolicitudRequest;
import utp.edu.denuncias.dto.SolicitudResponse;
import utp.edu.denuncias.dto.SolicitudRevisionRequest;
import utp.edu.denuncias.service.SolicitudService;

/**
 * Controlador para gestionar las solicitudes en el sistema.
 * Proporciona endpoints para la creación y revisión de solicitudes, interactuando
 * con la lógica de negocio a través de {@link SolicitudService}.
 */
@EnableMethodSecurity
@RestController
@RequiredArgsConstructor
public class SolicitudController {

    /**
     * Instancia del servicio {@link SolicitudService} utilizada para gestionar la lógica de negocio
     * relacionada con las solicitudes. Proporciona métodos para realizar y revisar solicitudes,
     * interactuando con los datos persistidos en el sistema.
     */
    private final SolicitudService solicitudService;

    /**
     * Maneja la creación de una nueva solicitud en el sistema utilizando los datos proporcionados.
     *
     * @param request objeto {@link SolicitudRequest} que contiene la información necesaria para crear la solicitud,
     *                incluyendo título, mensaje, ID de la denuncia, ID del revisor opcional y el tipo de solicitud.
     */
    @PostMapping("/mod/solicitud")
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
    @PutMapping("/mod/solicitud")
    public ResponseEntity<SolicitudResponse> aceptarSolicitud(@RequestBody SolicitudRevisionRequest request) {
        return ResponseEntity.ok(solicitudService.revisarSolicitud(request));
    }
}
