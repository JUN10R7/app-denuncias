package utp.edu.denuncias.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import utp.edu.denuncias.dto.DenunciaRequest;
import utp.edu.denuncias.dto.DenunciaResponse;
import utp.edu.denuncias.model.Denuncia;
import utp.edu.denuncias.service.DenunciaService;

import java.util.List;

/**
 * Controlador REST encargado de manejar las operaciones relacionadas con las denuncias.
 */
@EnableMethodSecurity
@RestController
@RequiredArgsConstructor
public class DenunciaController {

    /** Servicio que contiene la lógica de negocio para la gestión de denuncias */
    private final DenunciaService denunciaService;

    /**
     * Obtiene las denuncias asociadas a un usuario específico.
     *
     * @param id ID del usuario
     * @return Lista de respuestas {@link DenunciaResponse} asociadas al usuario
     */
    @GetMapping("/usuario/denuncia")
    public List<DenunciaResponse> getDenuncias(@RequestParam("id") Long id) {
        return denunciaService.listarDenunciasUsuario(id);
    }

    /**
     * Registra una nueva denuncia realizada por un usuario.
     *
     * @param request Objeto que contiene los datos de la denuncia
     * @return Respuesta con los datos de la denuncia registrada
     */
    @PostMapping("/usuario/denuncia")
    public DenunciaResponse nuevaDenuncia(@RequestBody DenunciaRequest request) {
        return denunciaService.nuevaDenuncia(request);
    }

    /**
     * Elimina una denuncia específica mediante su ID.
     *
     * @param id ID de la denuncia a eliminar
     */
    @DeleteMapping("/usuario/denuncia")
    public void eliminarDenuncia(@RequestParam("id") Long id) {
        denunciaService.eliminarDenuncia(id);
    }

    /**
     * Obtiene todas las denuncias del sistema (acceso para moderadores).
     *
     * @return Lista completa de denuncias
     */
    @GetMapping("/mod/denuncia")
    public ResponseEntity<List<DenunciaResponse>> getAllDenuncias() {
        return ResponseEntity.ok(denunciaService.listarDenuncias());
    }
}
