package utp.edu.denuncias.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import utp.edu.denuncias.dto.SolicitudRequest;
import utp.edu.denuncias.dto.SolicitudResponse;
import utp.edu.denuncias.dto.SolicitudRevisionRequest;
import utp.edu.denuncias.enums.Estado;
import utp.edu.denuncias.enums.Rol;
import utp.edu.denuncias.model.Solicitud;
import utp.edu.denuncias.model.Usuario;
import utp.edu.denuncias.repository.DenunciaRepository;
import utp.edu.denuncias.repository.SolicitudRepository;
import utp.edu.denuncias.repository.UserRepository;
import utp.edu.denuncias.security.JwtUtil;

/**
 * Servicio para gestionar las solicitudes en el sistema.
 * Proporciona la lógica necesaria para crear y resolver solicitudes relacionadas
 * con denuncias, incluyendo la asignación de revisores y el cambio de estado.
 */
@Service
@RequiredArgsConstructor
public class SolicitudService {

    /**
     * Repositorio para interactuar con la entidad {@link Solicitud} en la base de datos.
     * Proporciona métodos para realizar operaciones básicas de persistencia, tales como
     * guardar, actualizar, eliminar y buscar solicitudes.
     */
    private final SolicitudRepository solicitudRepository;
    /**
     * Repositorio utilizado para interactuar con la entidad {@link Usuario}.
     * Se encarga de realizar operaciones de manipulación y consulta sobre los datos
     * de los usuarios en el sistema.
     */
    private final UserRepository userRepository;
    /**
     * Repositorio utilizado para realizar operaciones relacionadas con la entidad Denuncia.
     * Proporciona acceso a los métodos para consultar, guardar, actualizar y eliminar denuncias
     * en la base de datos.
     */
    private final DenunciaRepository denunciaRepository;

    /**
     * Crea y persiste una nueva solicitud en el sistema en base a los datos proporcionados.
     * La solicitud puede incluir un revisor asignado si es requerida y si el autor posee
     * el rol adecuado (por ejemplo, ADMIN).
     *
     * @param request Objeto {@link SolicitudRequest} que contiene la información necesaria para crear la solicitud,
     *                incluyendo título, mensaje, ID de la denuncia, ID del revisor opcional y el tipo de solicitud.
     */
    public void realizarSolicitud(SolicitudRequest request) {
        var autor = userRepository.findByUsername(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario autenticado"));
        var denuncia = denunciaRepository.findById(request.idDenuncia())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener la denuncia con ID " + request.idDenuncia()));
        Usuario revisor = null;
        if (autor.getRol().equals(Rol.ADMIN) && request.idRevisor() != null) {
            revisor = userRepository.findById(request.idRevisor())
                    .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario con ID " + request.idRevisor()));
        }
        Solicitud solicitud = Solicitud.builder()
                .titulo(request.titulo())
                .msg(request.msg())
                .denuncia(denuncia)
                .autor(autor)
                .revisor(revisor)
                .tipoSolicitud(request.tipoSolicitud())
                .build();
        solicitudRepository.save(solicitud);
    }

    /**
     * Rechaza y actualiza el estado de una solicitud en función de los datos proporcionados.
     * Este método también asigna el usuario revisor y guarda los cambios en la base de datos.
     *
     * @param request objeto {@link SolicitudRevisionRequest} que contiene el ID de la solicitud,
     *                el estado de aprobación/rechazo, y un mensaje opcional de respuesta.
     * @return una instancia de {@link SolicitudResponse} que representa el estado actualizado
     *         de la solicitud tras haber sido procesada.
     * @throws RuntimeException si no se encuentra el usuario autenticado o la solicitud con el ID proporcionado.
     */
    @Transactional
    public SolicitudResponse revisarSolicitud(SolicitudRevisionRequest request) {
        var revisor = userRepository.findByUsername(JwtUtil.getCurrentUsername())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener el usuario autenticado"));
        var solicitud = solicitudRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("No se pudo obtener la solicitud con ID " + request.id()));
        solicitud.setEstado(request.aprobado() ? Estado.RESUELTO : Estado.RECHAZADO);
        solicitud.setMsg(request.mensajeRespuesta());
        solicitud.setRevisor(revisor);
        return SolicitudResponse.from(solicitudRepository.save(solicitud));
    }

}
