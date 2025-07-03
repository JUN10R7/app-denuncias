package utp.edu.denuncias.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import utp.edu.denuncias.dto.DenunciaCambiarEstadoRequest;
import utp.edu.denuncias.dto.DenunciaRequest;
import utp.edu.denuncias.dto.DenunciaResponse;
import utp.edu.denuncias.service.DenunciaService;

import java.util.List;

@EnableMethodSecurity
@RestController
@RequiredArgsConstructor
public class DenunciaController {

    /** Servicio que contiene la lógica de negocio para la gestión de denuncias */
    private final DenunciaService denunciaService;

    /**
     * Recupera una lista de denuncias asociadas al usuario actualmente autenticado.
     *
     * @return una {@code ResponseEntity} que contiene una lista de objetos {@code DenunciaResponse},
     *         representando las denuncias del usuario autenticado.
     */
    @GetMapping("/usuario/denuncia")
    public ResponseEntity<List<DenunciaResponse>> getDenuncias() {
        return ResponseEntity.ok(denunciaService.listarDenunciasUsuario());
    }

    /**
     * Recupera una denuncia específica mediante su ID.
     *
     * @param id Identificador único de la denuncia que se desea obtener.
     * @return Una respuesta HTTP que contiene un objeto {@code DenunciaResponse}
     *         con los datos de la denuncia encontrada.
     */
    @GetMapping("/usuario/denuncia/{id}")
    public ResponseEntity<DenunciaResponse> getDenuncia(@PathVariable Long id) {
        return ResponseEntity.ok(denunciaService.listarDenuncia(id));
    }

    /**
     * Recupera una lista de denuncias asignadas a moderadores, basada en el estado del parámetro ingresado.
     *
     * @param all Indica si se deben obtener todas las denuncias sin moderador asignado
     *            o solo las asignadas al moderador actualmente autenticado.
     * @return Una entidad de respuesta que contiene una lista de objetos {@code DenunciaResponse},
     *         representando las denuncias obtenidas según los criterios especificados.
     */
    @GetMapping("/mod/denuncia/{all}")
    public ResponseEntity<List<DenunciaResponse>> getAllDenunciasMod(@PathVariable Boolean all) {
        return ResponseEntity.ok(denunciaService.listarDenunciasMod(all));
    }

    /**
     * Recupera una lista con todas las denuncias registradas en el sistema.
     *
     * @return una respuesta HTTP que contiene una lista de objetos {@code DenunciaResponse}
     *         que representan todas las denuncias disponibles.
     */
    @GetMapping("/admin/denuncia")
    public ResponseEntity<List<DenunciaResponse>> getAllDenuncias() {
        return ResponseEntity.ok(denunciaService.listarAllDenuncias());
    }

    /**
     * Obtiene una lista de todas las denuncias asociadas a un usuario específico,
     * identificado por su ID.
     *
     * @param id ID del usuario cuyas denuncias se desean recuperar
     * @return Respuesta que contiene una lista de las denuncias del usuario en formato {@link DenunciaResponse}
     */
    @GetMapping("/admin/denuncia/usuario/{id}")
    public ResponseEntity<List<DenunciaResponse>> getAllDenunciasUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(denunciaService.listarAllDenunciasUsuario(id));
    }

    /**
     * Obtiene todas las denuncias asignadas a un moderador específico identificado por su ID.
     *
     * @param id ID del moderador cuyas denuncias se desean obtener
     * @return una respuesta HTTP que contiene una lista de objetos {@code DenunciaResponse}
     *         representando las denuncias asignadas al moderador
     */
    @GetMapping("/admin/denuncia/mod/{id}")
    public ResponseEntity<List<DenunciaResponse>> getAllDenunciasMod(@PathVariable Long id) {
        return ResponseEntity.ok(denunciaService.listarAllDenunciasMod(id));
    }

    /**
     * Registra una nueva denuncia enviada por el usuario autenticado.
     *
     * @param request Objeto {@code DenunciaRequest} que contiene los detalles de la denuncia a registrar.
     * @return Una respuesta HTTP con un objeto {@code DenunciaResponse}
     *         que representa la información de la denuncia registrada.
     */
    @PostMapping("/usuario/denuncia")
    public ResponseEntity<DenunciaResponse> nuevaDenuncia(@RequestBody DenunciaRequest request) {
        return ResponseEntity.ok(denunciaService.nuevaDenuncia(request));
    }

    /**
     * Actualiza los datos de una denuncia específica identificada por su ID.
     *
     * @param id ID de la denuncia que se desea editar.
     * @param request Objeto {@code DenunciaRequest} que contiene los datos actualizados de la denuncia.
     * @return Una respuesta HTTP que incluye un objeto {@code DenunciaResponse} con los datos de la denuncia actualizada.
     */
    @PutMapping("/usuario/denuncia/{id}")
    public ResponseEntity<DenunciaResponse> editarDenuncia(@PathVariable Long id,  @RequestBody DenunciaRequest request) {
        return ResponseEntity.ok(denunciaService.editarDenuncia(id, request));
    }

    /**
     * Cambia el estado de una denuncia identificada en la solicitud recibida.
     *
     * @param request Objeto {@code DenunciaCambiarEstadoRequest} que contiene el ID de la denuncia
     *                y el nuevo estado que se debe asignar.
     * @return Una respuesta HTTP que incluye un objeto {@code DenunciaResponse}
     *         con los detalles de la denuncia actualizada después de modificar su estado.
     */
    @PutMapping("/admin/denuncia/cambiarEstado")
    public ResponseEntity<DenunciaResponse> cambiarEstadoDenuncia(@RequestBody DenunciaCambiarEstadoRequest request) {
        return ResponseEntity.ok(denunciaService.cambiarEstadoDenuncia(request));
    }

    /**
     * Asigna un moderador a una denuncia específica.
     *
     * @param idDenuncia ID de la denuncia a la que se desea asignar un moderador.
     * @param idMod ID del moderador que se asignará a la denuncia.
     * @return Una respuesta HTTP que contiene un objeto {@code DenunciaResponse}
     *         con los detalles de la denuncia actualizada.
     */
    @PutMapping("/admin/denuncia/asignarMod/{idDenuncia}/{idMod}")
    public ResponseEntity<DenunciaResponse> asignarModerador(@PathVariable Long idDenuncia, @PathVariable Long idMod) {
        return ResponseEntity.ok(denunciaService.asignarModerador(idDenuncia, idMod));
    }

    /**
     * Elimina una denuncia específica basada en su identificador único.
     *
     * @param id Identificador único de la denuncia que se desea eliminar.
     */
    @DeleteMapping("/usuario/denuncia")
    public void eliminarDenuncia(@RequestParam("id") Long id) {
        denunciaService.eliminarDenuncia(id);
    }

}
